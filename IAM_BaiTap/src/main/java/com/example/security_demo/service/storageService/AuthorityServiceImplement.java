package com.example.security_demo.service.storageService;

import com.evo.common.UserAuthority;
import com.evo.common.webapp.security.AuthorityService;
import com.example.security_demo.entity.Role;
import com.example.security_demo.entity.User;
import com.example.security_demo.repository.IRoleRepository;
import com.example.security_demo.repository.IRoleUserRepository;
import com.example.security_demo.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class AuthorityServiceImplement implements AuthorityService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final com.example.security_demo.service.AuthorityService authorityService;
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
