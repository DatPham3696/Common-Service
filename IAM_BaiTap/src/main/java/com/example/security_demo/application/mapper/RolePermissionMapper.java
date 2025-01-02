package com.example.security_demo.application.mapper;

import com.example.security_demo.application.dto.request.role.UpdateRolePermissionRequest;
import com.example.security_demo.domain.command.RolePermissionCmd;
import com.example.security_demo.domain.domainEntity.RolePermission;
import com.example.security_demo.infrastructure.persistance.entity.RolePermissionEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolePermissionMapper {

  RolePermissionCmd fromRequest(UpdateRolePermissionRequest request);

  List<RolePermission> fromListRolePermissionEntity(List<RolePermissionEntity> listEntity);

  List<RolePermissionEntity> fromListRolePermissonDomain(List<RolePermission> domain);
}
