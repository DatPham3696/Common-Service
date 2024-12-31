package com.example.security_demo.infrastructure.repository.repoImpl;

import com.example.security_demo.application.mapper.RoleUserMapper;
import com.example.security_demo.application.mapper.UserMapper;
import com.example.security_demo.domain.domainEntity.RoleUser;
import com.example.security_demo.domain.domainEntity.User;
import com.example.security_demo.infrastructure.entity.RoleUserEntity;
import com.example.security_demo.infrastructure.entity.UserEntity;
import com.example.security_demo.domain.repository.IUserRepository;
import com.example.security_demo.infrastructure.persistance.JpaRoleUserRepository;
import com.example.security_demo.infrastructure.persistance.JpaUserRepository;
import org.hibernate.boot.jaxb.internal.stax.JpaOrmXmlEventReader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryImpl implements IUserRepository {
    private final JpaUserRepository iUserRepositoryJpa;
    private final UserMapper userMapper;
    private final RoleUserRepositoryImpl roleUserRepository;
    private final RoleUserMapper roleUserMapper;
    private final JpaRoleUserRepository roleUserRepositoryJpa;

    public UserRepositoryImpl(JpaUserRepository iUserRepositoryJpa,
                              UserMapper userMapper,
                              RoleUserRepositoryImpl roleUserRepository,
                              RoleUserMapper roleUserMapper,
                              JpaRoleUserRepository roleUserRepositoryJpa) {
        this.iUserRepositoryJpa = iUserRepositoryJpa;
        this.userMapper = userMapper;
        this.roleUserRepository = roleUserRepository;
        this.roleUserMapper = roleUserMapper;
        this.roleUserRepositoryJpa = roleUserRepositoryJpa;
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
    public Optional<User> findUserById(String userId) {
        Optional<UserEntity> entity = iUserRepositoryJpa.findById(userId);
        return entity.map(userMapper::fromUserEntity);
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
    public Optional<User> findUserByEmail(String email) {
        Optional<UserEntity> optionalEntity = iUserRepositoryJpa.findByEmail(email);
        return optionalEntity.map(userMapper::fromUserEntity);
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
        if (user.getRoleUser() != null) {
            roleUserRepository.saveAll(user.getRoleUser());
        }
        return iUserRepositoryJpa.save(userEntity);
    }

    @Override
    public void saveAfterAddRole(User user) {
        User userData = userMapper.fromUserEntity(findById(user.getId()).get());
        if (userData.getRoleUser() == null || userData.getRoleUser().isEmpty()) {
            List<RoleUserEntity> roleUserEntityList = roleUserMapper.toRoleEntityList(user.getRoleUser());
            roleUserRepositoryJpa.saveAll(roleUserEntityList);
        } else {
            List<RoleUser> roleUserList = user.getRoleUser().stream()
                    .filter(item1 -> userData.getRoleUser().stream().noneMatch(item2 -> item1.getUserId().equals(item2.getUserId())
                    && item1.getRoleId().equals(item2.getRoleId())
                    )).toList();
            roleUserRepositoryJpa.saveAll(roleUserMapper.toRoleEntityList(roleUserList));
        }
    }

}
