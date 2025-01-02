package com.example.security_demo.domain.repository;


import com.example.security_demo.domain.domainEntity.Role;
import com.example.security_demo.infrastructure.persistance.entity.RoleEntity;

import java.util.List;
import java.util.Optional;

public interface IRoleDomainRepository {

  boolean save(Role role);
}
