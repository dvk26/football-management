package com.webapp.ftm.repository;

import com.webapp.ftm.model.GoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface GoalRepository extends JpaRepository<GoalEntity, UUID> {
//    @Query(value = "Call saveGoal(:matchId, :playerId, :teamId, :goalType, :timeScore);",nativeQuery = true)
    @Procedure(name = "goal.saveGoal")
    void saveGoal(@Param("matchId")UUID matchId, @Param("playerId")UUID ownerGoal, @Param("teamId") UUID teamId, @Param("goalType")Long goalType, @Param("timeScore")Long timeScore);
}
