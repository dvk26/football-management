package com.webapp.ftm.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TeamStatisticDTO {
    private UUID teamId;
    private UUID seasonId;
    private UUID tournamentId;
    private Long score;
    private Long goalDifference;
    private Long playedMatches;
    private Long wonMatches;
    private Long lostMatches;
    private Long drawnMatches;
}
