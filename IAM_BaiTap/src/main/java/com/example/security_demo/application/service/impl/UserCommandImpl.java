package com.example.security_demo.application.service.impl;

import com.example.security_demo.application.dto.request.user.RegisterDTO;
import com.example.security_demo.application.dto.request.user.UpdateInforRequestDTO;
import com.example.security_demo.application.mapper.UserCommandMapper;
import com.example.security_demo.application.service.RoleService;
import com.example.security_demo.application.service.UserCommandService;
import com.example.security_demo.application.service.UserKeycloakService;
import com.example.security_demo.domain.command.RegisterCommand;
import com.example.security_demo.domain.command.UpdateInforCommand;
import com.example.security_demo.domain.domainEntity.User;
import com.example.security_demo.domain.exception.UserExistedException;
import com.example.security_demo.domain.repository.IUserRepository;
import com.example.security_demo.infrastructure.repository.repoImpl.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserCommandImpl implements UserCommandService {
    private final UserCommandMapper userCommandMapper;
    private final IUserRepository userRepository;
    private final UserRepositoryImpl userRepositoryImpl;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserKeycloakService userKeycloakService;

    @Override
    public String createUser(RegisterDTO registerDTO) throws UserExistedException { // bo dto, chuyen dto -> request
        RegisterCommand registerCommand = userCommandMapper.fromRegisterDto(registerDTO);

        if (userRepositoryImpl.existsByEmail(registerCommand.getEmail())) {
            throw new UserExistedException("Email already exists");
        }

        if (registerCommand.getRolesId() == null) {
            throw new RuntimeException("Role not found " + registerCommand.getRolesId());
        }

        List<Long> roleExists = roleService.listRoleExist(registerCommand.getRolesId());
        String verificationCode = UUID.randomUUID().toString().substring(0, 6);
        User userDomain = new User(registerCommand,
                passwordEncoder,
                verificationCode,
                () -> userKeycloakService.getKeycloakUserId(userCommandMapper.from(registerCommand)),
                roleExists);
         userRepositoryImpl.save(userDomain);
         return "Create account successful";
    }

    @Override
    public String updateUser(UpdateInforRequestDTO updateInforRequestDTO) {
        UpdateInforCommand updateInforCommand = userCommandMapper.fromUpdateInforDto(updateInforRequestDTO);

        return "";
    }

}
