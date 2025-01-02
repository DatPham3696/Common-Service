package com.example.security_demo.application.service;

import com.example.security_demo.infrastructure.persistance.entity.PermissionEntity;
import com.example.security_demo.infrastructure.persistance.entity.RoleEntity;
import com.example.security_demo.infrastructure.persistance.entity.RolePermissionEntity;
import com.example.security_demo.infrastructure.persistance.repository.JpaPermissionRepository;
import com.example.security_demo.infrastructure.persistance.repository.JpaRolePermissionRepository;
import com.example.security_demo.infrastructure.persistance.repository.JpaRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RolePermissionService {

  private final JpaRolePermissionRepository rolePermissionRepository;
  private final JpaRoleRepository roleRepository;
  private final JpaPermissionRepository permissionRepository;

  public RolePermissionEntity addRolePermission(String code, String resourceCode, String scope) {
    RoleEntity role = roleRepository.findByCode(code)
        .orElseThrow(() -> new RuntimeException("Not found role"));
    PermissionEntity permission = permissionRepository.findByResourceCodeAndScope(resourceCode,
            scope)
        .orElseThrow(() -> new RuntimeException("Not found permission"));
    boolean check = rolePermissionRepository.findAllByRoleId(role.getId()).stream()
        .anyMatch(rolePermission -> rolePermission.getPermissionId().equals(permission.getId()));
    if (check) {
      throw new RuntimeException("Role permission existed");
    }
    return rolePermissionRepository.save(RolePermissionEntity.builder()
        .permissionId(permission.getId())
        .roleId(role.getId())
        .build());
  }
}
