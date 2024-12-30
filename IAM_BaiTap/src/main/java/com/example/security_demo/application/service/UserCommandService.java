package com.example.security_demo.application.service;

import com.example.security_demo.application.dto.request.user.RegisterDTO;
import com.example.security_demo.application.dto.request.user.UpdateInforRequestDTO;
import com.example.security_demo.domain.exception.UserExistedException;

public interface UserCommandService {
    String createUser(RegisterDTO registerDTO) throws UserExistedException;
    String updateUser(UpdateInforRequestDTO updateInforRequestDTO);
}
