package com.webapp.ftm.repository;

import com.webapp.ftm.model.TournamentSeasonEntity;
import com.webapp.ftm.model.pk.TournamentSeasonPk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TournamentSeasonRepository extends JpaRepository<TournamentSeasonEntity,TournamentSeasonPk> {
    Optional<TournamentSeasonEntity> findByTournamentSeasonPk_TournamentEntity_IdAndTournamentSeasonPk_SeasonEntity_Id(UUID tournamentId, UUID seasonId);
}
