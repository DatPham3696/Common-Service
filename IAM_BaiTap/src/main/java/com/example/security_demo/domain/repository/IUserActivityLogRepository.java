package com.example.security_demo.domain.repository;

import com.example.security_demo.infrastructure.entity.UserActivityLogEntity;

public interface IUserActivityLogRepository{
    UserActivityLogEntity save(UserActivityLogEntity userActivityLog);
}
