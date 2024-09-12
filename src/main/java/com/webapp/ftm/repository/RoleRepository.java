package com.webapp.ftm.repository;

import com.webapp.ftm.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
    public RoleEntity findByName(String name);

}
