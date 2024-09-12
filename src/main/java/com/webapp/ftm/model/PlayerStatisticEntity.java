package com.webapp.ftm.model;

import com.webapp.ftm.model.pk.PlayerStatisticPk;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="playerstatistic")
@Setter
@Getter
public class PlayerStatisticEntity {
    @EmbeddedId
    private PlayerStatisticPk playerStatisticPk;

    @Column(name="goalscore")
    private Long goalScore;

}
