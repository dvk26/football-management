package com.webapp.ftm.repository;

import com.webapp.ftm.model.TeamEntity;
import org.hibernate.id.uuid.UuidGenerator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, UUID> {
    @Query(value = "Select u FROM TeamEntity u,TeamStatisticEntity t " +
            "WHERE u.id = t.teamStatisticPk.teamEntity.id AND t.teamStatisticPk.tournamentSeasonEntity.tournamentSeasonPk.tournamentEntity.id = :tournamentId AND t.teamStatisticPk.tournamentSeasonEntity.tournamentSeasonPk.seasonEntity.id = :seasonId", nativeQuery = false)

    List<TeamEntity> findAllByTournamentAndSeason(@Param("tournamentId")UUID tournamentId, @Param("seasonId")UUID seasonId);
}
