package com.example.security_demo.domain.repository;

import com.example.security_demo.application.dto.request.user.UserSearchRequest;
import com.example.security_demo.domain.entity.User;

import java.util.List;

public interface IUserRepositoryCustom {
    List<User> searchUser(UserSearchRequest request);
    Long countUser(UserSearchRequest request);
}
