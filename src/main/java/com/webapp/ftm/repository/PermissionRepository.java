package com.webapp.ftm.repository;

import com.webapp.ftm.model.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<PermissionEntity,Long> {
    boolean existsByModuleAndApiPathAndMethod(String module, String apiPath, String method);
}
