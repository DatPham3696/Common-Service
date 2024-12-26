package com.example.security_demo.infrastructure.repository.repoImpl;

import com.example.security_demo.infrastructure.persistance.UserActivityLog;
import com.example.security_demo.domain.repository.IUserActivityLogRepository;
import com.example.security_demo.infrastructure.repository.IUserActivityLogRepositoryJpa;
import org.springframework.stereotype.Component;

@Component
public class UserActivityLogRepositoryImpl implements IUserActivityLogRepository {
    private final IUserActivityLogRepositoryJpa userActivityLogRepositoryJpa;

    public UserActivityLogRepositoryImpl(IUserActivityLogRepositoryJpa userActivityLogRepositoryJpa) {
        this.userActivityLogRepositoryJpa = userActivityLogRepositoryJpa;
    }

    @Override
    public UserActivityLog save(UserActivityLog userActivityLog) {
        return userActivityLogRepositoryJpa.save(userActivityLog);
    }

}
