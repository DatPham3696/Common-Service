package com.example.security_demo.infrastructure.persistance.repository;

import com.example.security_demo.application.dto.request.user.UserSearchRequest;
import com.example.security_demo.infrastructure.persistance.entity.UserEntity;

import java.util.List;

public interface JpaUserRepositoryCustom {

  List<UserEntity> searchUser(UserSearchRequest request);

  Long countUser(UserSearchRequest request);
}
