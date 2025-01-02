package com.example.security_demo.application.service;

import com.example.security_demo.application.dto.request.user.RegisterRequest;
import com.example.security_demo.application.dto.request.user.RoleUserRequest;
import com.example.security_demo.application.dto.request.user.UpdateInforRequest;
import com.example.security_demo.domain.exception.UserExistedException;

public interface UserCommandService {

  String createUser(RegisterRequest registerDTO) throws UserExistedException;

  String updateUser(UpdateInforRequest updateInforRequestDTO) throws UserExistedException;

  String softDeleted(String userId);

  String updateRoleUser(RoleUserRequest roleUserRequest);
}
