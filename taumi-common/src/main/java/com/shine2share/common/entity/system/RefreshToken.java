package com.shine2share.common.entity.system;

import javax.persistence.*;

@Entity
@Table(name = "T_SYS_REFRESH_TOKEN")
public class RefreshToken {
    @Id
    @Column(name = "TOKEN")
    private String token;

    @Column(name = "EXPIREAT")
    private Long expireAt;

    @Column(name = "USERID")
    private String userId;

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpireAt() {
        return this.expireAt;
    }

    public void setExpireAt(Long expireat) {
        this.expireAt = expireat;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userid) {
        this.userId = userid;
    }
}
