package com.example.security_demo.domain.repository;

import com.example.security_demo.domain.domainEntity.User;
import com.example.security_demo.infrastructure.persistance.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUserDomainRepository {

  boolean existsByEmail(String email);

  boolean save(User user);

  Optional<User> findUserByEmail(String email);

  Optional<User> findUserById(String userId);

}

