package com.example.security_demo.domain.repository;

import com.example.security_demo.infrastructure.entity.PermissionEntity;


import java.util.Optional;

public interface IPermissionRepository  {
    Optional<PermissionEntity> findByScope(String scope);

    Optional<PermissionEntity> findByResourceCode(String resourceCode);

    Optional<PermissionEntity> findByResourceCodeAndScope(String resourceCode, String scope);

    Optional<Long> findPermissionIdByUserAndResourceCodeAndScope(String userID,
                                                                 String resourceCode,
                                                                 String scope);

    boolean existsByScopeAndResourceCode(String scope, String resourceCode);
    Optional<PermissionEntity> findById(Long permissionId);
}

