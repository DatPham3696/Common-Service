package com.example.security_demo.presentation.rest;

import com.example.security_demo.application.dto.request.permission.SoftDeletePermissionRequest;
import com.example.security_demo.infrastructure.persistance.entity.PermissionEntity;
import com.example.security_demo.application.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/permission")
public class PermissionController {

  private final PermissionService permissionService;

  @PostMapping("/create-permission")
  @PreAuthorize("hasPermission('PERMISSION','CREATE')")
  public ResponseEntity<PermissionEntity> addPermission(@RequestBody PermissionEntity permission) {
    return ResponseEntity.ok().body(permissionService.addPermission(permission));
  }

  @PostMapping("/soft-permission-delete/{permissionId}")
  @PreAuthorize("hasPermission('PERMISSION','DELETE')")
  public ResponseEntity<?> softDeletePermission(@PathVariable("permissionId") Long permissionId,
      @RequestBody SoftDeletePermissionRequest request) {
    return ResponseEntity.ok().body(permissionService.softDelete(permissionId, request));
  }

  @GetMapping("/permission-paging")
  @PreAuthorize("hasPermission('PERMISSION','VIEW')")
  public ResponseEntity<?> getPermissions(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return ResponseEntity.ok().body(permissionService.getPermissions(pageable));
  }
}
