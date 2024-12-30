package com.example.security_demo.infrastructure.persistance;

import com.example.security_demo.infrastructure.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository // imple userRepocus
public interface JpaUserRepository extends JpaRepository<UserEntity, String> {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    Optional<UserEntity> findByUserName(String userName);

    Optional<UserEntity> findById(String userId);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

    UserEntity findByVerificationCode(String verificationCode);

    UserEntity findByKeyclUserId(String keycloakUserId);

    @Query(value = "SELECT * FROM users u " +
            "WHERE unaccent(u.username || ' ' || u.email || ' ' || u.phone_number || ' ' || u.address) " +
            "ILIKE unaccent(CONCAT('%', :keyword, '%'))", nativeQuery = true)
    Page<UserEntity> findByKeyWord(@Param("keyword") String keyword, Pageable pageable);
}

