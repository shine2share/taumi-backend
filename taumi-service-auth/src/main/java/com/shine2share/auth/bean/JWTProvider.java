package com.shine2share.auth.bean;

import com.shine2share.auth.enums.ErrorCode;
import com.shine2share.auth.enums.TokenType;
import com.shine2share.common.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JWTProvider {
    public static final String SCOPES = "scope";
    public static final String AUTHORITIES = "authorities";
    public static final String USER_ID = "userId";
    private static final String STORE_PASS = "Iloveyou@1992";
    private static final String FILE_PRIVATE_KEY = "mykey.keystore";
    private static final String ALIAS = "mykey";

    @Value("${rsa.private.key:empty}")
    private String privateKey;

    @Value("${jwt.access.validity}")
    private long jwtAccessValidity;

    @Value("${jwt.refresh.validity}")
    private long jwtRefreshValidity;

    @Value("${jwt.check.validity}")
    private long jwtCheckValidity;

    public String generateToken(
            String userId,
            List<String> scopes,
            List<String> authorities,
            String uuId,
            TokenType tokenType,
            Date expireAt)
            throws BusinessException {
        Claims claims = Jwts.claims();
        if (TokenType.ACCESS_TOKEN.equals(tokenType)) {
            claims.put(SCOPES, scopes);
            claims.put(AUTHORITIES, authorities);
        }
        claims.put(USER_ID, userId);
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setExpiration(expireAt)
                .addClaims(claims)
                .signWith(SignatureAlgorithm.RS256, getPrivateKey())
                .setId(uuId)
                .compact();
    }

    public Date generateExpireAt(TokenType tokenType) {
        if (tokenType.equals(TokenType.ACCESS_TOKEN)) {
            return new Date(new Date().getTime() + jwtAccessValidity);
        }
        if (tokenType.equals(TokenType.REFRESH_TOKEN)) {
            return new Date(new Date().getTime() + jwtRefreshValidity);
        }
        return new Date(new Date().getTime() + jwtCheckValidity);
    }

    private PrivateKey getPrivateKey() throws BusinessException {
        if ("empty".equals(privateKey)) {
            return new KeyStoreKeyFactory(
                    new ClassPathResource(FILE_PRIVATE_KEY), STORE_PASS.toCharArray())
                    .getKeyPair(ALIAS)
                    .getPrivate();
        } else {
            try {
                privateKey =
                        privateKey
                                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                                .replaceAll(System.lineSeparator(), "")
                                .replace("-----END RSA PRIVATE KEY-----", "");
                byte[] decoded = Base64.getDecoder().decode(privateKey);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
                return keyFactory.generatePrivate(keySpec);
            } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
                throw new BusinessException(ErrorCode.SERVER_ERROR, e.getStackTrace());
            }
        }
    }
}
