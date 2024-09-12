package com.webapp.ftm.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="goal")
@NamedStoredProcedureQuery(name = "goal.saveGoal",
        procedureName = "saveGoal", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "matchId", type = UUID.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "playerId", type = UUID.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "teamId", type = UUID.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "goalType", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "timeScore", type = Long.class)})
public class GoalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name="goaltype")
    private String type;

    @Column(name="timescore")
    private Long timeScore;

    @ManyToOne
    @JoinColumn(name = "goalowner", referencedColumnName = "id")
    private PlayerEntity playerEntity;

    @ManyToOne
    @JoinColumn(name = "matchid", referencedColumnName = "id")
    private MatchEntity matchEntity;
}
