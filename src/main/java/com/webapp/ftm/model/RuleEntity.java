package com.webapp.ftm.model;

import com.webapp.ftm.enums.RankingOrderDetail;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Table(name="rule")
public class RuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "minage")
    private Long minAge;

    @Column(name = "maxage")
    private Long maxAge;

    @Column(name="minnumofplayers")
    private Long minNumOfPlayers;

    @Column(name="maxnumofplayers")
    private Long maxNumOfPlayers;

    @Column(name="maxnumofforeigners")
    private Long maxNumOfForeigners;

    @Column(name="maxtimetoscore")
    private Long maxTimeToScore;

    @Column(name="numoftypescore")
    private Long numOfTypeScore;

    @Column(name="winscore")
    private Long winScore;

    @Column(name="losescore")
    private Long loseScore;

    @Column(name="drawnscore")
    private Long drawnScore;

    @Column(name = "rankingorder")
    private RankingOrderDetail rankingOrderDetail = RankingOrderDetail.DEFAULT;

    @Column(name = "timecreated", updatable = false, nullable = false)
    private LocalDateTime timeCreated;

}
