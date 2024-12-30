package com.example.security_demo.domain.repository;

import com.example.security_demo.infrastructure.entity.RolePermissionEntity;
import java.util.List;

public interface IRolePermissionRepository {
    List<RolePermissionEntity> findAllByRoleId(Long roleId);
    boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId);
}
