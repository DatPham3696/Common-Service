package com.example.security_demo.infrastructure.persistance.repository.repoImpl;

import com.example.security_demo.infrastructure.persistance.entity.UserActivityLogEntity;
import com.example.security_demo.domain.repository.IUserActivityLogDomainRepository;
import com.example.security_demo.infrastructure.persistance.repository.JpaUserActivityLogRepository;
import org.springframework.stereotype.Component;

@Component
public class UserActivityLogRepositoryImpl implements IUserActivityLogDomainRepository {

  private final JpaUserActivityLogRepository userActivityLogRepositoryJpa;

  public UserActivityLogRepositoryImpl(JpaUserActivityLogRepository userActivityLogRepositoryJpa) {
    this.userActivityLogRepositoryJpa = userActivityLogRepositoryJpa;
  }

  @Override
  public UserActivityLogEntity save(UserActivityLogEntity userActivityLog) {
    return userActivityLogRepositoryJpa.save(userActivityLog);
  }

}
