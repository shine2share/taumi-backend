package com.shine2share.system.service;

import com.google.common.hash.Hashing;
import com.shine2share.common.BusinessException;
import com.shine2share.common.entity.system.RefreshToken;
import com.shine2share.common.entity.system.Role;
import com.shine2share.common.entity.system.User;
import com.shine2share.system.bean.JWTProvider;
import com.shine2share.system.dto.LoginResponse;
import com.shine2share.system.enums.ErrorCode;
import com.shine2share.system.enums.GrantType;
import com.shine2share.system.enums.TokenType;
import com.shine2share.system.repository.RefreshTokenRepository;
import com.shine2share.system.repository.RoleRepository;
import com.shine2share.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class LoginService {
    @Value("${jwt.access.validity}")
    private Long jwtAccessValidity;
    private final UserRepository userRepository;
    private final JWTProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RoleRepository roleRepository;
    @Autowired
    public LoginService(
            UserRepository userRepository,
            JWTProvider jwtProvider,
            RefreshTokenRepository refreshTokenRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.refreshTokenRepository = refreshTokenRepository;
        this.roleRepository = roleRepository;
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
        List<String> scope = Arrays.asList("admin");
        List<String> roles = getRoleByUser(userId);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(
                jwtProvider.generateToken(
                        userId,
                        scope,
                        roles,
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
            refreshToken.setExpireAt(refreshTokenExpireAt.getTime());
            refreshToken.setUserId(userId);
            refreshTokenRepository.save(refreshToken);
        }
        return loginResponse;
    }
    private String hashToken(String token) {
        return Hashing.sha256().hashString(token, StandardCharsets.UTF_8).toString();
    }
    private List<String> getRoleByUser(String userId) {
        List<String> roles = new ArrayList<>();
        List<Role> roleByUserId = roleRepository.getRoleByUserId(userId);
        for (Role sr : roleByUserId) {
            if (sr.getRoleid() != null) {
                roles.add(sr.getRoleid());
            }
        }
        return roles;
    }
    @Transactional
    public void delete(String token) {
        refreshTokenRepository.deleteByToken(hashToken(token));
    }
}
