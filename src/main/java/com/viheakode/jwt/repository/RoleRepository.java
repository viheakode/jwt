package com.viheakode.jwt.repository;

import com.viheakode.jwt.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String roleName);

    boolean existsByRoleName(String roleName);

    @Query(value = """
            SELECT r.role_name
            FROM tbl_role r
            JOIN tbl_user_role ur ON r.role_id = ur.role_id
            WHERE ur.user_id = :userId
            """, nativeQuery = true)
    List<String> findRoleNameByUserId(@Param("userId") Long userId);
}
