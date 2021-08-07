package com.shine2share.system.dto;

import java.util.List;

public class LoginResponse {
    private String accessToken;
    private Long timeExpiration;
    private String refreshToken;
    private String userId;
    private String userStatus;
    private List<String> scopes;
    private List<String> authorizes;
    private UserInfoResponse userInfo;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getTimeExpiration() {
        return timeExpiration;
    }

    public void setTimeExpiration(Long timeExpiration) {
        this.timeExpiration = timeExpiration;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public List<String> getAuthorizes() {
        return authorizes;
    }

    public void setAuthorizes(List<String> authorizes) {
        this.authorizes = authorizes;
    }

    public UserInfoResponse getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoResponse userInfo) {
        this.userInfo = userInfo;
    }
}
