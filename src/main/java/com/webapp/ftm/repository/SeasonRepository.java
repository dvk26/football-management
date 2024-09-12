package com.webapp.ftm.repository;

import com.webapp.ftm.model.SeasonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SeasonRepository extends JpaRepository<SeasonEntity, UUID> {

    List<SeasonEntity> findByName(String name);

}
