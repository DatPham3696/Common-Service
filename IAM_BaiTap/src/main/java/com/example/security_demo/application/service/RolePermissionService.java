package com.example.security_demo.application.service;

import com.example.security_demo.infrastructure.persistance.Permission;
import com.example.security_demo.infrastructure.persistance.Role;
import com.example.security_demo.infrastructure.persistance.RolePermission;
import com.example.security_demo.infrastructure.repository.IPermissionRepositoryJpa;
import com.example.security_demo.infrastructure.repository.IRolePermissionRepositoryJpa;
import com.example.security_demo.infrastructure.repository.IRoleRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RolePermissionService {
    private final IRolePermissionRepositoryJpa rolePermissionRepository;
    private final IRoleRepositoryJpa roleRepository;
    private final IPermissionRepositoryJpa permissionRepository;

    public RolePermission addRolePermission(String code, String resourceCode, String scope) {
        Role role = roleRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Not found role"));
        Permission permission = permissionRepository.findByResourceCodeAndScope(resourceCode, scope)
                                .orElseThrow(() -> new RuntimeException("Not found permission"));
        boolean check = rolePermissionRepository.findAllByRoleId(role.getId()).stream()
                        .anyMatch(rolePermission -> rolePermission.getPermissionId().equals(permission.getId()));
        if (check) {
            throw new RuntimeException("Role permission existed");
        }
        return rolePermissionRepository.save(RolePermission.builder()
                .permissionId(permission.getId())
                .roleId(role.getId())
                .build());
    }
}
