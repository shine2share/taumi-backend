package com.shine2share.system.repository;

import com.shine2share.common.entity.system.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    @Query("select ro from Role ro\n" +
            "join UserRole ur on ro.roleid=ur.roleid\n" +
            "where ur.userid=:userId")
    List<Role> getRoleByUserId(@Param("userId") String userId);
}
