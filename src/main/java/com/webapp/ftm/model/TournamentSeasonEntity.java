package com.webapp.ftm.model;

import com.webapp.ftm.model.pk.TournamentSeasonPk;
import com.webapp.ftm.enums.TournamentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter

@Table(name = "tournament_season")
public class TournamentSeasonEntity {
    @EmbeddedId
    private TournamentSeasonPk tournamentSeasonPk;

    private TournamentStatus status;
    @ManyToOne
    @JoinColumn(name = "winner", referencedColumnName = "id")
    private TeamEntity winner;

    @ManyToOne
    @JoinColumn(name="ruleid", referencedColumnName = "id")
    private RuleEntity rule;

    @OneToMany(mappedBy = "teamStatisticPk.tournamentSeasonEntity", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<TeamStatisticEntity> teamStatisticEntities;

}
