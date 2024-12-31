package com.example.security_demo.application.service;

import com.example.security_demo.application.dto.request.role.CreateRoleRequest;

public interface RoleCommandService {
    String createRole(CreateRoleRequest request);
}
