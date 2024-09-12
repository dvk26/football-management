package com.webapp.ftm.model;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="matches")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class MatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name="round")
    private Long round;

    @Column(name="date")
    private Date date;

    @Column(name="time")
    private Time time;

    @Column(name="team1score")
    private Long team1Score ;

    @Column(name="team2score")
    private Long team2Score ;

    @ManyToOne
    @JoinColumn(name = "hometeam", referencedColumnName = "id")
    private TeamEntity homeTeam;

    @ManyToOne
    @JoinColumn(name = "awayteam", referencedColumnName = "id")
    private TeamEntity awayTeam;

    @ManyToOne(targetEntity = TournamentSeasonEntity.class, optional = false)
    @JoinColumns({
            @JoinColumn(name = "tournamentid",referencedColumnName = "tournamentid"),
            @JoinColumn(name = "seasonid", referencedColumnName = "seasonid")})
    private TournamentSeasonEntity tournamentSeasonEntity;

}
