package com.example.security_demo.application.mapper;

import com.example.security_demo.domain.domainEntity.User;
import com.example.security_demo.infrastructure.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity from(User user);
    User fromUserEntity(UserEntity userEntity);
}
