package com.example.security_demo.infrastructure.repository.repoImpl;

import com.example.security_demo.infrastructure.entity.UserActivityLogEntity;
import com.example.security_demo.domain.repository.IUserActivityLogRepository;
import com.example.security_demo.infrastructure.persistance.JpaUserActivityLogRepository;
import org.springframework.stereotype.Component;

@Component
public class UserActivityLogRepositoryImpl implements IUserActivityLogRepository {
    private final JpaUserActivityLogRepository userActivityLogRepositoryJpa;

    public UserActivityLogRepositoryImpl(JpaUserActivityLogRepository userActivityLogRepositoryJpa) {
        this.userActivityLogRepositoryJpa = userActivityLogRepositoryJpa;
    }

    @Override
    public UserActivityLogEntity save(UserActivityLogEntity userActivityLog) {
        return userActivityLogRepositoryJpa.save(userActivityLog);
    }

}
