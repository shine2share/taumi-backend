package com.shine2share.common.entity.system;

import javax.persistence.*;

@Entity
@Table(name = "T_SYS_ROLE")
public class Role {
    @Id
    @Column(name = "ROLEID")
    private String roleid;

    @Column(name = "ROLENAME")
    private String rolename;

    @Column(name = "PARENTID")
    private String parentid;

    @Column(name = "CREATEDBY")
    private String createdby;

    @Column(name = "CREATEDTIME")
    private java.sql.Timestamp createdtime;

    @Column(name = "UPDATEDBY")
    private String updatedby;

    @Column(name = "ISACTIVE")
    private Long isactive;

    public String getRoleid() {
        return this.roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return this.rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getParentid() {
        return this.parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getCreatedby() {
        return this.createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public java.sql.Timestamp getCreatedtime() {
        return this.createdtime;
    }

    public void setCreatedtime(java.sql.Timestamp createdtime) {
        this.createdtime = createdtime;
    }

    public String getUpdatedby() {
        return this.updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public Long getIsactive() {
        return this.isactive;
    }

    public void setIsactive(Long isactive) {
        this.isactive = isactive;
    }
}
