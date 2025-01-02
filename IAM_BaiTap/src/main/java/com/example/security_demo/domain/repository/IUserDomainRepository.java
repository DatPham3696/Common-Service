package com.example.security_demo.domain.repository;

import com.example.security_demo.domain.domainEntity.User;
import com.example.security_demo.infrastructure.persistance.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUserDomainRepository {

  Optional<UserEntity> findByUserName(String userName);

  Optional<UserEntity> findById(String userId);

  Optional<User> findUserById(String userId);

  boolean existsByEmail(String email);

  Optional<UserEntity> findByEmail(String email);

  Optional<User> findUserByEmail(String email);

  UserEntity findByVerificationCode(String verificationCode);

  UserEntity findByKeyclUserId(String keycloakUserId);

  Page<UserEntity> findByKeyWord(String keyword, Pageable pageable);

  UserEntity save(User user);
//    void saveAfterAddRole(User user);
}

