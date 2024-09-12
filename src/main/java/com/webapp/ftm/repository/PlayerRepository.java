package com.webapp.ftm.repository;


import com.webapp.ftm.model.PlayerEntity;
import com.webapp.ftm.repository.custom.PlayerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlayerRepository extends JpaRepository<PlayerEntity, UUID>, PlayerRepositoryCustom {
}
