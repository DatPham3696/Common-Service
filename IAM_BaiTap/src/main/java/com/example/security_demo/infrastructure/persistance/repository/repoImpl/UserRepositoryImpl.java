package com.example.security_demo.infrastructure.persistance.repository.repoImpl;

import com.example.security_demo.application.mapper.RoleUserMapper;
import com.example.security_demo.application.mapper.UserMapper;
import com.example.security_demo.domain.domainEntity.RoleUser;
import com.example.security_demo.domain.domainEntity.User;
import com.example.security_demo.infrastructure.persistance.entity.RoleUserEntity;
import com.example.security_demo.infrastructure.persistance.entity.UserEntity;
import com.example.security_demo.domain.repository.IUserDomainRepository;
import com.example.security_demo.infrastructure.persistance.repository.JpaRoleUserRepository;
import com.example.security_demo.infrastructure.persistance.repository.JpaUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryImpl implements IUserDomainRepository {

  private final JpaUserRepository iUserRepositoryJpa;
  private final UserMapper userMapper;
  private final RoleUserRepositoryImpl roleUserRepository;


  public UserRepositoryImpl(JpaUserRepository iUserRepositoryJpa,
      UserMapper userMapper,
      RoleUserRepositoryImpl roleUserRepository) {
    this.iUserRepositoryJpa = iUserRepositoryJpa;
    this.userMapper = userMapper;
    this.roleUserRepository = roleUserRepository;

  }

  @Override
  public Optional<User> findUserById(String userId) {
    Optional<UserEntity> entity = iUserRepositoryJpa.findById(userId);
    return entity.map(userMapper::fromUserEntity);
  }

  @Override
  public boolean existsByEmail(String email) {
    return iUserRepositoryJpa.existsByEmail(email);
  }

  @Override
  public Optional<User> findUserByEmail(String email) {
    Optional<UserEntity> optionalEntity = iUserRepositoryJpa.findByEmail(email);
    return optionalEntity.map(userMapper::fromUserEntity);
  }

  @Override
  public boolean save(User user) {
    UserEntity userEntity = userMapper.from(user);
    if (user.getRoleUser() != null) {
      roleUserRepository.saveAll(user.getRoleUser());
    }
    iUserRepositoryJpa.save(userEntity);
    return true;
  }


}
