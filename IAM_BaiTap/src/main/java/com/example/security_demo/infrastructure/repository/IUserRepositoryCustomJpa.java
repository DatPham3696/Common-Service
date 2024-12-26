package com.example.security_demo.infrastructure.repository;

import com.example.security_demo.application.dto.request.user.UserSearchRequest;
import com.example.security_demo.infrastructure.persistance.User;

import java.util.List;

public interface IUserRepositoryCustomJpa {
    List<User> searchUser(UserSearchRequest request);
    Long countUser(UserSearchRequest request);
}
