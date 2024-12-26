package com.example.security_demo.infrastructure.repository.repoImpl;

import com.example.security_demo.infrastructure.persistance.User;
import com.example.security_demo.domain.repository.IUserRepository;
import com.example.security_demo.infrastructure.repository.IUserRepositoryJpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryImpl implements IUserRepository {
    private final IUserRepositoryJpa iUserRepositoryJpa;

    public UserRepositoryImpl(IUserRepositoryJpa iUserRepositoryJpa) {
        this.iUserRepositoryJpa = iUserRepositoryJpa;
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return iUserRepositoryJpa.findByUserName(userName);
    }

    @Override
    public Optional<User> findById(String userId) {
        return iUserRepositoryJpa.findById(userId);
    }

    @Override
    public boolean existsByEmail(String email) {
        return iUserRepositoryJpa.existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return iUserRepositoryJpa.findByEmail(email);
    }

    @Override
    public User findByVerificationCode(String verificationCode) {
        return iUserRepositoryJpa.findByVerificationCode(verificationCode);
    }

    @Override
    public User findByKeyclUserId(String keycloakUserId) {
        return iUserRepositoryJpa.findByKeyclUserId(keycloakUserId);
    }

    @Override
    public Page<User> findByKeyWord(String keyword, Pageable pageable) {
        return iUserRepositoryJpa.findByKeyWord(keyword, pageable);
    }

    @Override
    public User save(User user) {
        return iUserRepositoryJpa.save(user);
    }
}
