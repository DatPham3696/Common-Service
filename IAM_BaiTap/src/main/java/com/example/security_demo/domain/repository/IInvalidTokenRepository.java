package com.example.security_demo.domain.repository;

import com.example.security_demo.domain.entity.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInvalidTokenRepository extends JpaRepository<InvalidToken,String> {
}
