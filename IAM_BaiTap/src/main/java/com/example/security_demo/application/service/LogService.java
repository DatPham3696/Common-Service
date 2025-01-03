package com.example.security_demo.application.service;

import com.example.security_demo.infrastructure.persistance.entity.UserActivityLogEntity;
import com.example.security_demo.infrastructure.persistance.repository.repoImpl.UserActivityLogRepositoryImpl;
import org.springframework.stereotype.Service;

@Service
public class LogService {

  private final UserActivityLogRepositoryImpl userActivityLogRepository;

  public LogService(UserActivityLogRepositoryImpl userActivityLogRepository) {
    this.userActivityLogRepository = userActivityLogRepository;
  }

  public void saveLog(UserActivityLogEntity userActivityLog) {
    userActivityLogRepository.save(userActivityLog);
  }
}
