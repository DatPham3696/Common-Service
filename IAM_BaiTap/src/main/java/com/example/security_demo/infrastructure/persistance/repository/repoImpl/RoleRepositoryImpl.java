package com.example.security_demo.infrastructure.persistance.repository.repoImpl;

import com.example.security_demo.application.mapper.RoleMapper;
import com.example.security_demo.domain.domainEntity.Role;
import com.example.security_demo.infrastructure.persistance.entity.RoleEntity;
import com.example.security_demo.domain.repository.IRoleDomainRepository;
import com.example.security_demo.infrastructure.persistance.repository.JpaRoleRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RoleRepositoryImpl implements IRoleDomainRepository {

  private final JpaRoleRepository roleRepositoryJpa;
  private final RoleMapper mapper;

  public RoleRepositoryImpl(JpaRoleRepository roleRepositoryJpa, RoleMapper mapper) {
    this.roleRepositoryJpa = roleRepositoryJpa;
    this.mapper = mapper;
  }

  @Override
  public Optional<RoleEntity> findByCode(String code) {
    return roleRepositoryJpa.findByCode(code);
  }

  @Override
  public List<RoleEntity> findAll(List<Long> id) {
    return roleRepositoryJpa.findAllById(id);
  }

  @Override
  public RoleEntity save(Role role) {
    RoleEntity roleEntity = mapper.fromRoleDomain(role);
    return roleRepositoryJpa.save(roleEntity);
  }

}
