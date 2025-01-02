package com.example.security_demo.application.mapper;

import com.example.security_demo.domain.domainEntity.Role;
import com.example.security_demo.infrastructure.persistance.entity.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

  RoleEntity fromRoleDomain(Role role);

  Role fromRoleEntity(RoleEntity roleEntity);
}
