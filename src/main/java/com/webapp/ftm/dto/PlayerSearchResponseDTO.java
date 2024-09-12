package com.webapp.ftm.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class PlayerSearchResponseDTO {
    private UUID id;
    private String name;
    private Date dateOfBirth;
    private String type;
    private String note;
    private String tournamentSeasons;
    private Long  totalGoals;
    private String team;
}
