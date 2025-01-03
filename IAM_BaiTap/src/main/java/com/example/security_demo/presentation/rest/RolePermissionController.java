package com.example.security_demo.presentation.rest;

import com.example.security_demo.infrastructure.persistance.entity.RolePermissionEntity;
import com.example.security_demo.application.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role-permission")
@RequiredArgsConstructor
public class RolePermissionController {

  private final RolePermissionService rolePermissionService;

  @PostMapping("/create-role-permission")
  @PreAuthorize("hasPermission('ROLE','CREATE')")
  public ResponseEntity<RolePermissionEntity> createRolePermission(
      @RequestParam("code") String code,
      @RequestParam("resource_code") String resourceCode,
      @RequestParam("scope") String scope) {
    return ResponseEntity.ok()
        .body(rolePermissionService.addRolePermission(code, resourceCode, scope));
  }
}
