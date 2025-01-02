package com.example.security_demo.presentation.rest;

import com.example.security_demo.application.dto.request.role.CreateRoleRequest;
import com.example.security_demo.application.dto.request.role.SoftDeleteRoleRequest;
import com.example.security_demo.application.service.impl.RoleCommandImpl;
import com.example.security_demo.application.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

  private final RoleService roleService;
  private final RoleCommandImpl roleCommand;

  @PostMapping("/create-role")
  @PreAuthorize("hasPermission('ROLE','CREATE')")
  public ResponseEntity<?> addRole(@RequestBody CreateRoleRequest request) {
//        RoleEntity createdRole = roleService.addRole(role);
    return ResponseEntity.ok().body(roleCommand.createRole(request));
  }

  @PostMapping("/soft-role-delete/{code}")
  @PreAuthorize("hasPermission('ROLE','DELETE')")
  public ResponseEntity<?> softDelete(@PathVariable("code") String code,
      @RequestBody SoftDeleteRoleRequest request) {
    return ResponseEntity.ok().body(roleService.softDelete(code, request));
  }

  @GetMapping("roles-paging")
  @PreAuthorize("hasPermission('ROLE','VIEW')")
  public ResponseEntity<?> getRoles(@RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return ResponseEntity.ok().body(roleService.getRoles(pageable));
  }
}
