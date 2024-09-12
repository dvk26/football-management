package com.webapp.ftm.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TeamDTO {
    private UUID id;
    private UUID seasonId;
    private UUID tournamentId;
    private String homeCourt;
    private String name;
}
