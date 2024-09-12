package com.webapp.ftm.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;


@Getter
@Setter
public class PlayerSearchRequestDTO {
    private UUID teamId;
    private UUID tournamentId;
    private UUID seasonId;
    private String name;
    private Long yearOfBirth;
    private String type;
    private String note;
    private Long  goalScoresFrom;
    private Long goalScoresTo;
}
