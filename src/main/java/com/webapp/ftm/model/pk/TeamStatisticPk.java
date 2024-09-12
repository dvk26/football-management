package com.webapp.ftm.model.pk;

import com.webapp.ftm.model.TeamEntity;
import com.webapp.ftm.model.TournamentEntity;
import com.webapp.ftm.model.TournamentSeasonEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class TeamStatisticPk implements Serializable {
    @ManyToOne
    @JoinColumn(name = "teamid", referencedColumnName = "id")
    private TeamEntity teamEntity;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "tournamentid",referencedColumnName = "tournamentid"),
            @JoinColumn(name = "seasonid", referencedColumnName = "seasonid")}
    )
    private TournamentSeasonEntity tournamentSeasonEntity;
}
