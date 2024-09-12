package com.webapp.ftm.repository;

import com.webapp.ftm.model.TeamStatisticEntity;
import com.webapp.ftm.model.pk.TeamStatisticPk;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeamStatisticRepository extends JpaRepository<TeamStatisticEntity, TeamStatisticPk> {
    @Query("select u from TeamStatisticEntity u WHERE u.teamStatisticPk.tournamentSeasonEntity.tournamentSeasonPk.tournamentEntity.id = :tournamentId AND u.teamStatisticPk.tournamentSeasonEntity.tournamentSeasonPk.seasonEntity.id = :seasonId")
    List<TeamStatisticEntity> findAllBy_TournamentId_And_SeasonId(@Param("tournamentId")UUID tournamentId,@Param("seasonId") UUID seasonId);
    @Query("select u from TeamStatisticEntity u WHERE u.teamStatisticPk.tournamentSeasonEntity.tournamentSeasonPk.tournamentEntity.id = :tournamentId AND u.teamStatisticPk.tournamentSeasonEntity.tournamentSeasonPk.seasonEntity.id = :seasonId AND u.teamStatisticPk.teamEntity.id = :teamId")
    TeamStatisticEntity findAllBy_TournamentId_And_SeasonId_And_TeamId(@Param("tournamentId")UUID tournamentId,@Param("seasonId") UUID seasonId, @Param("teamId")UUID teamId);

}
