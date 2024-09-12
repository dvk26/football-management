package com.webapp.ftm.model.pk;

import com.webapp.ftm.model.RuleEntity;
import com.webapp.ftm.model.SeasonEntity;
import com.webapp.ftm.model.TournamentEntity;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TournamentSeasonPk implements Serializable {
    @ManyToOne
    @JoinColumn(name = "tournamentid", referencedColumnName = "id")
    private TournamentEntity tournamentEntity;

    @ManyToOne
    @JoinColumn(name = "seasonid", referencedColumnName = "id")
    private SeasonEntity seasonEntity;

}
