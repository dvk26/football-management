package com.webapp.ftm.repository;

import com.webapp.ftm.model.TournamentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.util.UUID;

public interface TournamentRepository extends JpaRepository<TournamentEntity, UUID> {
    List<TournamentEntity> findByName(String name);
}
