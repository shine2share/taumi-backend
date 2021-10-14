package com.shine2share.common.entity.system;

import javax.persistence.*;

@Entity
@Table(name = "T_SYS_USER_ROLE")
public class UserRole {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERID")
    private String userid;

    @Column(name = "ROLEID")
    private String roleid;

    @Column(name = "ISACTIVE")
    private Long isactive;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRoleid() {
        return this.roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public Long getIsactive() {
        return this.isactive;
    }

    public void setIsactive(Long isactive) {
        this.isactive = isactive;
    }
}
