package com.webapp.ftm.repository;

import com.webapp.ftm.model.RuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RuleRepository extends JpaRepository<RuleEntity, UUID> {

    List<RuleEntity> findAllByOrderByTimeCreatedDesc();
}
