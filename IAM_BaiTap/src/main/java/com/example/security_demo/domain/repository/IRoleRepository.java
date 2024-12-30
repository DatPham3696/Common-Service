package com.example.security_demo.domain.repository;


import com.example.security_demo.infrastructure.entity.RoleEntity;
import org.apache.poi.sl.draw.geom.GuideIf;

import java.util.List;
import java.util.Optional;

public interface IRoleRepository  {
    Optional<RoleEntity> findByCode(String code);
    List<RoleEntity> findAll(List<Long> id );

}
