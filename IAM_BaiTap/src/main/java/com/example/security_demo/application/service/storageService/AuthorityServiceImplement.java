package com.example.security_demo.application.service.storageService;

import com.evo.common.UserAuthority;
import com.evo.common.webapp.security.AuthorityService;
import com.example.security_demo.domain.entity.User;
import com.example.security_demo.domain.repository.IRoleRepository;
import com.example.security_demo.domain.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class AuthorityServiceImplement implements AuthorityService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final com.example.security_demo.application.service.AuthorityService authorityService;
    @Override
    public UserAuthority getUserAuthority(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("Username not found during getUserAuthority() "));
        return UserAuthority.builder()
                .userId(user.getId())
                .isRoot(user.getIsRoot())
                .grantedPermissions(authorityService.getAuthority(email))
                .build();
    }

    @Override
    public UserAuthority getClientAuthority(UUID clientId) {
        return null;
    }
}
