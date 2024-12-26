package com.example.security_demo.infrastructure.repository;

import com.example.security_demo.infrastructure.persistance.UserActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserActivityLogRepositoryJpa extends JpaRepository<UserActivityLog,Long> {
}
