package com.example.security_demo.domain.repository;


import com.example.security_demo.application.dto.request.user.UserSearchRequest;
import com.example.security_demo.infrastructure.persistance.entity.UserEntity;

import java.util.List;

public interface IUserRepositoryDomainCustom {

  List<UserEntity> searchUser(UserSearchRequest request);

  Long countUser(UserSearchRequest request);
}
