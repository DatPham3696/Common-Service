package com.example.security_demo.application.service;

import com.example.security_demo.domain.entity.UserActivityLog;
import com.example.security_demo.domain.repository.IUserActivityLogRepository;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    private final IUserActivityLogRepository userActivityLogRepository;

    public LogService(IUserActivityLogRepository userActivityLogRepository) {
        this.userActivityLogRepository = userActivityLogRepository;
    }
    public void saveLog(UserActivityLog userActivityLog){
        userActivityLogRepository.save(userActivityLog);
    }
}
