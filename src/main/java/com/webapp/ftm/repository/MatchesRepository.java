package com.webapp.ftm.repository;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.webapp.ftm.dto.response.MatchResponseDTO;
import com.webapp.ftm.model.MatchEntity;
import com.webapp.ftm.model.TeamStatisticEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MatchesRepository extends JpaRepository<MatchEntity, UUID> {
    @Query("select u from MatchEntity u WHERE u.tournamentSeasonEntity.tournamentSeasonPk.tournamentEntity.id=:tournamentId AND u.tournamentSeasonEntity.tournamentSeasonPk.seasonEntity.id=:seasonId")
    List<MatchEntity> findAllBy_TournamentId_And_SeasonId(@Param("tournamentId")UUID tournamentId, @Param("seasonId") UUID seasonId);
    @Query (value = "SELECT COUNT(Matches.id) FROM MATCHES\n" +
            "WHERE tournamentid = :tournamentId AND seasonid = :seasonId\n" +
            "AND ((awayteam = :againstTeamId AND hometeam = :teamId AND team1score > team2score) OR (awayteam = :teamId AND hometeam = :againstTeamId AND team1score < team2score)) ", nativeQuery = true)
    int countMatchesWinningAgainst(@Param("teamId")UUID teamId, @Param("againstTeamId") UUID againstTeamId, @Param("tournamentId")UUID tournamentId,@Param("seasonId") UUID seasonId);
}
