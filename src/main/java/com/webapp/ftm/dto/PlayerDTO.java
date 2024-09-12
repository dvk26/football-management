package com.webapp.ftm.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class PlayerDTO {
    private UUID id;
    private UUID teamId;
    private UUID tournamentId;
    private UUID seasonId;
    private String name;
    private Date dateOfBirth;
    private String type;
    private String note;
}
