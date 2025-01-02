package com.example.security_demo.application.service.impl;

import com.example.security_demo.application.dto.request.role.CreateRoleRequest;
import com.example.security_demo.application.dto.request.role.UpdateRolePermissionRequest;
import com.example.security_demo.application.mapper.RoleCommandMapper;
import com.example.security_demo.application.mapper.RolePermissionMapper;
import com.example.security_demo.application.service.custom.RoleCommandService;
import com.example.security_demo.domain.command.CreateRoleCmd;
import com.example.security_demo.domain.command.RolePermissionCmd;
import com.example.security_demo.domain.domainEntity.Role;
import com.example.security_demo.domain.domainEntity.RolePermission;
import com.example.security_demo.domain.repository.IRoleDomainRepository;
import com.example.security_demo.domain.repository.IRolePermissionDomainRepository;
import com.example.security_demo.infrastructure.persistance.repository.repoImpl.RolePermissionRepositoryImpl;
import com.example.security_demo.infrastructure.persistance.repository.repoImpl.RoleRepositoryImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.convert.SpelIndexResolver;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleCommandImpl implements RoleCommandService {

  private final RoleCommandMapper roleCommandMapper;
  private final IRoleDomainRepository roleDomainRepository;
  private final RolePermissionMapper rolePermissionMapper;
  private final IRolePermissionDomainRepository rolePermissionDomainRepository;

  @Override
  public String createRole(CreateRoleRequest request) {
    CreateRoleCmd createRoleCmd = roleCommandMapper.fromCreateRequest(request);
    Role role = new Role(createRoleCmd);
    roleDomainRepository.save(role);
    return "Create role successful";
  }

  @Override
  public String updateRolePermission(UpdateRolePermissionRequest request) {
    Role role = roleDomainRepository.findByRoleId(request.getRoleId());
    RolePermissionCmd cmd = rolePermissionMapper.fromRequest(request);
    List<RolePermission> rolePermissionsExist = rolePermissionDomainRepository.findAllByRoleIds(
        request.getRoleId());
    role.updateRolePermission(rolePermissionsExist, cmd);
    roleDomainRepository.save(role);
    return "Update role permission successful";
  }


}
