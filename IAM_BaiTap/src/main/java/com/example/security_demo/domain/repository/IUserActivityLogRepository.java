package com.example.security_demo.domain.repository;

import com.example.security_demo.infrastructure.persistance.UserActivityLog;

public interface IUserActivityLogRepository{
    UserActivityLog save(UserActivityLog userActivityLog);
}
