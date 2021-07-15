package com.shine2share.common.entity.system;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "T_SYS_USER")
public class User {
    @Id
    @Column(name = "USERID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;

    @Basic
    @Column(name = "USERNAME")
    private String userName;
    @Basic
    @Column(name = "PASSWORD")
    private String password;
    @Basic
    @Column(name = "EMAIL")
    private String email;
    @Basic
    @Column(name = "CREATEDBY")
    private String createdBy;
    @Basic
    @Column(name = "CREATEDTIME")
    private java.sql.Timestamp createdTime;
    @Basic
    @Column(name = "UPDATEDBY")
    private String updatedBy;
    @Basic
    @Column(name = "UPDATEDTIME")
    private java.sql.Timestamp updatedTime;
    @Basic
    @Column(name = "ISACTIVE")
    private Long isActive;
    @Basic
    @Column(name = "USERTYPE")
    private String userType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Long getIsActive() {
        return isActive;
    }

    public void setIsActive(Long isActive) {
        this.isActive = isActive;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}