package com.example.security_demo.infrastructure.repository.repoImpl;

import com.example.security_demo.infrastructure.persistance.RolePermission;
import com.example.security_demo.domain.repository.IRolePermissionRepository;
import com.example.security_demo.infrastructure.repository.IRolePermissionRepositoryJpa;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RolePermissionRepositoryImpl implements IRolePermissionRepository {
    private final IRolePermissionRepositoryJpa rolePermissionRepositoryJpa;

    public RolePermissionRepositoryImpl(IRolePermissionRepositoryJpa rolePermissionRepositoryJpa) {
        this.rolePermissionRepositoryJpa = rolePermissionRepositoryJpa;
    }

    @Override
    public List<RolePermission> findAllByRoleId(Long roleId) {
        return rolePermissionRepositoryJpa.findAllByRoleId(roleId);
    }

    @Override
    public boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId) {
        return rolePermissionRepositoryJpa.existsByRoleIdAndPermissionId(roleId, permissionId);
    }
}
