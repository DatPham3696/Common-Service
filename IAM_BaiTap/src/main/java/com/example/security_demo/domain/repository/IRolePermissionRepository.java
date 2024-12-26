package com.example.security_demo.domain.repository;

import com.example.security_demo.infrastructure.persistance.RolePermission;
import java.util.List;

public interface IRolePermissionRepository {
    List<RolePermission> findAllByRoleId(Long roleId);
    boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId);
}
