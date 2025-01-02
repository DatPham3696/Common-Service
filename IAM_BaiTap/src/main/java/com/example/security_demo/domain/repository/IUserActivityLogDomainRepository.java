package com.example.security_demo.domain.repository;

import com.example.security_demo.infrastructure.persistance.entity.UserActivityLogEntity;

public interface IUserActivityLogDomainRepository {

  UserActivityLogEntity save(UserActivityLogEntity userActivityLog);
}
