package com.example.security_demo.infrastructure.repository.repoImpl;

import com.example.security_demo.infrastructure.entity.RolePermissionEntity;
import com.example.security_demo.domain.repository.IRolePermissionRepository;
import com.example.security_demo.infrastructure.persistance.JpaRolePermissionRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RolePermissionRepositoryImpl implements IRolePermissionRepository {
    private final JpaRolePermissionRepository rolePermissionRepositoryJpa;

    public RolePermissionRepositoryImpl(JpaRolePermissionRepository rolePermissionRepositoryJpa) {
        this.rolePermissionRepositoryJpa = rolePermissionRepositoryJpa;
    }

    @Override
    public List<RolePermissionEntity> findAllByRoleId(Long roleId) {
        return rolePermissionRepositoryJpa.findAllByRoleId(roleId);
    }

    @Override
    public boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId) {
        return rolePermissionRepositoryJpa.existsByRoleIdAndPermissionId(roleId, permissionId);
    }
}
