package com.webapp.ftm.model.pk;

import com.webapp.ftm.model.PlayerEntity;
import com.webapp.ftm.model.TeamEntity;
import com.webapp.ftm.model.TournamentEntity;
import com.webapp.ftm.model.TournamentSeasonEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Embeddable
@Getter
public class PlayerStatisticPk implements Serializable {

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "playerid", referencedColumnName = "id")
    private PlayerEntity playerEntity;

    @ManyToOne
    @JoinColumn(name = "teamid", referencedColumnName = "id")
    private TeamEntity teamEntity;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "tournamentid",referencedColumnName = "tournamentid"),
            @JoinColumn(name = "seasonid", referencedColumnName = "seasonid")})
    private TournamentSeasonEntity tournamentSeasonEntity;
}
