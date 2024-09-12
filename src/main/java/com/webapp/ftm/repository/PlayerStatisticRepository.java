package com.webapp.ftm.repository;

import com.webapp.ftm.model.PlayerStatisticEntity;
import com.webapp.ftm.model.pk.PlayerStatisticPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PlayerStatisticRepository extends JpaRepository<PlayerStatisticEntity, PlayerStatisticPk> {

    @Query("select u from PlayerStatisticEntity u WHERE u.playerStatisticPk.tournamentSeasonEntity.tournamentSeasonPk.tournamentEntity.id = :tournamentId AND u.playerStatisticPk.tournamentSeasonEntity.tournamentSeasonPk.seasonEntity.id = :seasonId" +
            " AND u.playerStatisticPk.teamEntity.id= :teamId")
    List<PlayerStatisticEntity> findAllByTournamentAndSeasonAndTeam(
            @Param("tournamentId")UUID tournamentId, @Param("seasonId")UUID seasonId, @Param("teamId")UUID teamId);
}
