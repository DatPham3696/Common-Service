package com.example.security_demo.application.service.custom;

import com.example.security_demo.application.dto.request.role.CreateRoleRequest;
import com.example.security_demo.application.dto.request.role.UpdateRolePermissionRequest;

public interface RoleCommandService {

  String createRole(CreateRoleRequest request);

  String updateRolePermission(UpdateRolePermissionRequest updateRolePermissionRequest);
}
