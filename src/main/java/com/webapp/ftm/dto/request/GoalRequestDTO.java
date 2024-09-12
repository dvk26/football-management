package com.webapp.ftm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class GoalRequestDTO {
    private UUID matchId;
    private UUID playerId;
    private UUID teamId;
    private Long goalType;
    private Long timeScore;
}
