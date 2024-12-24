package com.example.security_demo.domain.repository;

import com.example.security_demo.application.dto.request.userProfile.UserProfileSearchRequest;
import com.example.security_demo.domain.entity.UserProfile;

import java.util.List;

public interface IUserProfileRepositoryCustom {
    List<UserProfile> searchUser(UserProfileSearchRequest request);

}
