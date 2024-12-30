package com.example.security_demo.infrastructure.repository.repoImpl;

import com.example.security_demo.application.mapper.UserMapper;
import com.example.security_demo.domain.domainEntity.User;
import com.example.security_demo.infrastructure.entity.UserEntity;
import com.example.security_demo.domain.repository.IUserRepository;
import com.example.security_demo.infrastructure.persistance.JpaUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryImpl implements IUserRepository {
    private final JpaUserRepository iUserRepositoryJpa;
    private final UserMapper userMapper;
    private final RoleUserRepositoryImpl roleUserRepository;

    public UserRepositoryImpl(JpaUserRepository iUserRepositoryJpa, UserMapper userMapper, RoleUserRepositoryImpl roleUserRepository) {
        this.iUserRepositoryJpa = iUserRepositoryJpa;
        this.userMapper = userMapper;
        this.roleUserRepository = roleUserRepository;
    }

    @Override
    public Optional<UserEntity> findByUserName(String userName) {
        return iUserRepositoryJpa.findByUserName(userName);
    }

    @Override
    public Optional<UserEntity> findById(String userId) {
        return iUserRepositoryJpa.findById(userId);
    }

    @Override
    public boolean existsByEmail(String email) {
        return iUserRepositoryJpa.existsByEmail(email);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return iUserRepositoryJpa.findByEmail(email);
    }

    @Override
    public UserEntity findByVerificationCode(String verificationCode) {
        return iUserRepositoryJpa.findByVerificationCode(verificationCode);
    }

    @Override
    public UserEntity findByKeyclUserId(String keycloakUserId) {
        return iUserRepositoryJpa.findByKeyclUserId(keycloakUserId);
    }

    @Override
    public Page<UserEntity> findByKeyWord(String keyword, Pageable pageable) {
        return iUserRepositoryJpa.findByKeyWord(keyword, pageable);
    }

    @Override
    public UserEntity save(User user) {
        UserEntity userEntity = userMapper.from(user);
        roleUserRepository.saveAll(user.getRoleUser());
        return iUserRepositoryJpa.save(userEntity);
    }
}
