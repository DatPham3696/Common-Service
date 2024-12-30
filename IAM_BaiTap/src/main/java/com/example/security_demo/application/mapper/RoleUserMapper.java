package com.example.security_demo.application.mapper;

import com.example.security_demo.domain.domainEntity.RoleUser;
import com.example.security_demo.infrastructure.entity.RoleUserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleUserMapper {
    List<RoleUserEntity> toRoleEntityList(List<RoleUser> domain);
}
