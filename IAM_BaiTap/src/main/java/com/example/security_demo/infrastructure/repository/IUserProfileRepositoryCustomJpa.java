package com.example.security_demo.infrastructure.repository;

import com.example.security_demo.application.dto.request.userProfile.UserProfileSearchRequest;
import com.example.security_demo.infrastructure.persistance.UserProfile;

import java.util.List;

public interface IUserProfileRepositoryCustomJpa {
    List<UserProfile> searchUser(UserProfileSearchRequest request);

}
