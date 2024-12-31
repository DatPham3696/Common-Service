package com.example.security_demo.application.service.impl;

import com.example.security_demo.application.dto.request.role.CreateRoleRequest;
import com.example.security_demo.application.mapper.RoleCommandMapper;
import com.example.security_demo.application.service.RoleCommandService;
import com.example.security_demo.domain.command.CreateRoleCmd;
import com.example.security_demo.domain.domainEntity.Role;
import com.example.security_demo.infrastructure.repository.repoImpl.RoleRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleCommandImpl implements RoleCommandService {
    private final RoleCommandMapper roleCommandMapper;
    private final RoleRepositoryImpl roleRepository;
    @Override
    public String createRole(CreateRoleRequest request) {
        CreateRoleCmd createRoleCmd = roleCommandMapper.fromCreateRequest(request);
        Role role = new Role(createRoleCmd);
        roleRepository.save(role);
        return "Create role successful";
    }

}
