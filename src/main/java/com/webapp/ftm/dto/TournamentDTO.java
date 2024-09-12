package com.webapp.ftm.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TournamentDTO {
    private UUID id;
    private String name;
}
