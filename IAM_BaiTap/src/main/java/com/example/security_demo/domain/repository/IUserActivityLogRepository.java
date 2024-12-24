package com.example.security_demo.domain.repository;

import com.example.security_demo.domain.entity.UserActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserActivityLogRepository extends JpaRepository<UserActivityLog,Long> {
}
