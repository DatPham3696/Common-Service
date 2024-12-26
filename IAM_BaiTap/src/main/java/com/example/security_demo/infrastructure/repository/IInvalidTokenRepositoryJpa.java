package com.example.security_demo.infrastructure.repository;

import com.example.security_demo.infrastructure.persistance.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInvalidTokenRepositoryJpa extends JpaRepository<InvalidToken,String> {
}
