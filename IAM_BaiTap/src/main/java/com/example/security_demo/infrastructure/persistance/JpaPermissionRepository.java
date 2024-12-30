package com.example.security_demo.infrastructure.persistance;

import com.example.security_demo.infrastructure.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaPermissionRepository extends JpaRepository<PermissionEntity, Long> {
    Optional<PermissionEntity> findByScope(String scope);

    Optional<PermissionEntity> findByResourceCode(String resourceCode);

    Optional<PermissionEntity> findByResourceCodeAndScope(String resourceCode, String scope);

    @Query("SELECT p.id FROM PermissionEntity p " +
            "JOIN RolePermissionEntity rp ON p.id = rp.permissionId " +
            "JOIN RoleUserEntity ru ON rp.roleId = ru.roleId " +
            "JOIN UserEntity u ON ru.userId = u.id " +
            "WHERE u.id = :userId " +
            "AND p.resourceCode = :resourceCode " +
            "AND p.scope = :scope ")
    Optional<Long> findPermissionIdByUserAndResourceCodeAndScope(@Param("userId") String userID,
                                                                 @Param("resourceCode") String resourceCode,
                                                                 @Param("scope") String scope);

    boolean existsByScopeAndResourceCode(String scope, String resourceCode);
}
