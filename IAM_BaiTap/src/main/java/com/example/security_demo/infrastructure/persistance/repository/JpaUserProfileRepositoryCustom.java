package com.example.security_demo.infrastructure.persistance.repository;

import com.example.security_demo.application.dto.request.userProfile.UserProfileSearchRequest;
import com.example.security_demo.infrastructure.persistance.entity.UserProfile;

import java.util.List;

public interface JpaUserProfileRepositoryCustom {

  List<UserProfile> searchUser(UserProfileSearchRequest request);

}
