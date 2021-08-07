package com.shine2share.system.service;

import com.google.common.hash.Hashing;
import com.shine2share.common.BusinessException;
import com.shine2share.common.entity.system.RefreshToken;
import com.shine2share.common.entity.system.User;
import com.shine2share.system.bean.JWTProvider;
import com.shine2share.system.dto.LoginResponse;
import com.shine2share.system.enums.ErrorCode;
import com.shine2share.system.enums.GrantType;
import com.shine2share.system.enums.TokenType;
import com.shine2share.system.repository.RefreshTokenRepository;
import com.shine2share.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class LoginService {
    @Value("${jwt.access.validity}")
    private Long jwtAccessValidity;
    private final UserRepository userRepository;
    private final JWTProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    @Autowired
    public LoginService(
            UserRepository userRepository,
            JWTProvider jwtProvider,
            RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public LoginResponse generateToken(String header, GrantType grantType, String token) throws BusinessException {
        String userId = null;
        String password;
        if (GrantType.PASSWORD_GRANT.equals(grantType)) {
            if (header.startsWith("Basic ")) {
                userId = new String(Base64.getDecoder().decode(header.substring(6))).split(":")[0];
                if (userId == null) {
                    throw new BusinessException(ErrorCode.USER_NOT_FOUND);
                } else {
                    password = new String(Base64.getDecoder().decode(header.substring(6))).split(":")[1];
                }
                // check user info in DB
                User user = userRepository.getUserByUserId(userId);
                if (user == null) {
                    throw new BusinessException(ErrorCode.USER_NOT_FOUND);
                } else {
                    if (password != null
                            && !(new String(Base64.getDecoder().decode(user.getPassword()))).equals(password)) {
                        throw new BusinessException(ErrorCode.WRONG_PASSWORD);
                    }
                }
            }
        } else if (GrantType.REFRESH_TOKEN.equals(grantType)) {
            if (token == null || token.length() == 0) {
                throw new BusinessException(ErrorCode.REFRESH_TOKEN_REQUIRED);
            }
            // check refresh token in DB
            System.out.println("hashToken: " + hashToken(token));
            RefreshToken refreshToken = refreshTokenRepository.findById(hashToken(token))
                    .orElseThrow(() -> new BusinessException(ErrorCode.REFRESH_TOKEN_NOT_EXIST));
            // check refresh token expire
            if (refreshToken.getExpireAt() < new Date().getTime()) {
                refreshTokenRepository.delete(refreshToken);
                throw new BusinessException(ErrorCode.REFRESH_TOKEN_EXPIRED);
            }
            userId = refreshToken.getUserId();
        } else {
            throw new BusinessException(ErrorCode.GRANT_TYPE_NOT_SUPPORTED);
        }
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(
                jwtProvider.generateToken(
                        userId,
                        null,
                        null,
                        UUID.randomUUID().toString(),
                        TokenType.ACCESS_TOKEN,
                        jwtProvider.generateExpireAt(TokenType.ACCESS_TOKEN)));
        loginResponse.setTimeExpiration(this.jwtAccessValidity);
        loginResponse.setUserId(userId);

        if (GrantType.PASSWORD_GRANT.equals(grantType)) {
            Date refreshTokenExpireAt = jwtProvider.generateExpireAt(TokenType.REFRESH_TOKEN);
            String uuId = UUID.randomUUID().toString();
            loginResponse.setRefreshToken(uuId);
            RefreshToken refreshToken = new RefreshToken();
            refreshToken.setToken(hashToken(uuId));
            System.out.println("refresh token: " + hashToken(uuId));
            refreshToken.setExpireAt(refreshTokenExpireAt.getTime());
            refreshToken.setUserId(userId);
            refreshTokenRepository.save(refreshToken);
        }
        return loginResponse;
    }

    private String hashToken(String token) {
        return Hashing.sha256().hashString(token, StandardCharsets.UTF_8).toString();
    }

    @Transactional
    public void delete(String token) {
        refreshTokenRepository.deleteByToken(hashToken(token));
    }
}