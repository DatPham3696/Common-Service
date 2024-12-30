package com.example.security_demo.infrastructure.persistance;

import com.example.security_demo.application.dto.request.userProfile.UserProfileSearchRequest;
import com.example.security_demo.infrastructure.entity.UserProfile;

import java.util.List;

public interface JpaUserProfileRepositoryCustom {
    List<UserProfile> searchUser(UserProfileSearchRequest request);

}
