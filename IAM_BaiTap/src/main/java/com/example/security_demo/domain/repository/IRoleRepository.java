package com.example.security_demo.domain.repository;


import com.example.security_demo.infrastructure.persistance.Role;

import java.util.Optional;

public interface IRoleRepository  {
    Optional<Role> findByCode(String code);

}
