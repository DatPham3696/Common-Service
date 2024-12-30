package com.example.security_demo.infrastructure.persistance;

import com.example.security_demo.infrastructure.entity.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaInvalidTokenRepository extends JpaRepository<InvalidToken,String> {
}
