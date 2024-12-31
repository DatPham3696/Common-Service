package com.example.security_demo.application.mapper;

import com.example.security_demo.application.dto.request.role.CreateRoleRequest;
import com.example.security_demo.application.dto.request.user.RoleUserRequest;
import com.example.security_demo.domain.command.CreateRoleCmd;
import com.example.security_demo.domain.command.RoleUserCmd;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleCommandMapper {
    CreateRoleRequest fromCmd(CreateRoleCmd createRoleCmd);
    CreateRoleCmd fromCreateRequest(CreateRoleRequest createRoleRequest);
    RoleUserCmd fromUpdateRequest(RoleUserRequest request);
}
