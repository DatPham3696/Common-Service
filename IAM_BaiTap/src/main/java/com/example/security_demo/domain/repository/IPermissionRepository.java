package com.example.security_demo.domain.repository;

import com.example.security_demo.infrastructure.persistance.Permission;


import java.util.Optional;

public interface IPermissionRepository  {
    Optional<Permission> findByScope(String scope);

    Optional<Permission> findByResourceCode(String resourceCode);

    Optional<Permission> findByResourceCodeAndScope(String resourceCode, String scope);

    Optional<Long> findPermissionIdByUserAndResourceCodeAndScope(String userID,
                                                                 String resourceCode,
                                                                 String scope);

    boolean existsByScopeAndResourceCode(String scope, String resourceCode);
    Optional<Permission> findById(Long permissionId);
}

