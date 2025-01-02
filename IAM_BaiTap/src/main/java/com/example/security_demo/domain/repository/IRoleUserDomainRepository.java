package com.example.security_demo.domain.repository;

import com.example.security_demo.domain.domainEntity.RoleUser;
import com.example.security_demo.infrastructure.persistance.entity.RoleUserEntity;

import java.util.List;

public interface IRoleUserDomainRepository {

  List<RoleUser> findAllByUserId(String userId);

  boolean saveAll(List<RoleUser> domain);

  RoleUser findByRoleId(Long roleId);
}
