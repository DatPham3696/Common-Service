package com.example.security_demo.infrastructure.persistance.repository.repoImpl;

import com.example.security_demo.application.mapper.RoleUserMapper;
import com.example.security_demo.domain.domainEntity.RoleUser;
import com.example.security_demo.infrastructure.persistance.entity.RoleUserEntity;
import com.example.security_demo.domain.repository.IRoleUserDomainRepository;
import com.example.security_demo.infrastructure.persistance.repository.JpaRoleUserRepository;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class RoleUserRepositoryImpl implements IRoleUserDomainRepository {

  private final JpaRoleUserRepository roleUserRepositoryJpa;
  private final RoleUserMapper roleUserMapper;

  public RoleUserRepositoryImpl(JpaRoleUserRepository roleUserRepositoryJpa,
      RoleUserMapper roleUserMapper) {
    this.roleUserRepositoryJpa = roleUserRepositoryJpa;
    this.roleUserMapper = roleUserMapper;
  }

  @Override
  public RoleUserEntity findByUserId(String userId) {
    return roleUserRepositoryJpa.findByUserId(userId);
  }

  //    @Override
//    public List<RoleUserEntity> findAllByUserId(String userId) {
//        return roleUserRepositoryJpa.findAllByUserId(userId);
//    }
  @Override
  public List<RoleUser> findAllByUserId(String userId) {
    return roleUserMapper.toRoleUserDomainList(roleUserRepositoryJpa.findAllByUserId(userId));
  }

  @Override
  public boolean saveAll(List<RoleUser> domain) {
    List<RoleUserEntity> roleUserEntityList = roleUserMapper.toRoleEntityList(domain);
    roleUserRepositoryJpa.saveAll(roleUserEntityList);
    return true;
  }

  @Override
  public RoleUser findByRoleId(Long roleId) {
    RoleUserEntity roleUser = roleUserRepositoryJpa.findByRoleId(roleId);
    return roleUserMapper.toRoleUserDomain(roleUser);
  }

}
