package com.example.security_demo.application.service;

import com.example.security_demo.domain.entity.Permission;
import com.example.security_demo.domain.entity.Role;
import com.example.security_demo.domain.entity.RolePermission;
import com.example.security_demo.domain.repository.IPermissionRepository;
import com.example.security_demo.domain.repository.IRolePermissionRepository;
import com.example.security_demo.domain.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RolePermissionService {
    private final IRolePermissionRepository rolePermissionRepository;
    private final IRoleRepository roleRepository;
    private final IPermissionRepository permissionRepository;

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
