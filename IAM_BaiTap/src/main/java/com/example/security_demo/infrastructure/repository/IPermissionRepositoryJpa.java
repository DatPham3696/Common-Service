package com.example.security_demo.infrastructure.repository;

import com.example.security_demo.infrastructure.persistance.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPermissionRepositoryJpa extends JpaRepository<Permission, Long> {
    Optional<Permission> findByScope(String scope);

    Optional<Permission> findByResourceCode(String resourceCode);

    Optional<Permission> findByResourceCodeAndScope(String resourceCode, String scope);

    @Query("SELECT p.id FROM Permission p " +
            "JOIN RolePermission rp ON p.id = rp.permissionId " +
            "JOIN RoleUser ru ON rp.roleId = ru.roleId " +
            "JOIN User u ON ru.userId = u.id " +
            "WHERE u.id = :userId " +
            "AND p.resourceCode = :resourceCode " +
            "AND p.scope = :scope ")
    Optional<Long> findPermissionIdByUserAndResourceCodeAndScope(@Param("userId") String userID,
                                                                 @Param("resourceCode") String resourceCode,
                                                                 @Param("scope") String scope);

    boolean existsByScopeAndResourceCode(String scope, String resourceCode);
}
