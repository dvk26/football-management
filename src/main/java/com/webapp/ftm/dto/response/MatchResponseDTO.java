package com.webapp.ftm.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class MatchResponseDTO {
    private UUID id;
    private Long round;
    private Date date;
    private Time time;
    private Long team1Score;
    private Long team2Score;
    private String homeTeamString;
    private UUID homeTeamId;
    private List<PlayerResponseDTO> homeTeamPlayers;
    private List<PlayerResponseDTO> awayTeamPlayers;
    private String awayTeamString;
    private UUID awayTeamId;
    private String venue;
}
