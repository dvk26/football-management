package com.webapp.ftm.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamStatisticResponseDTO  {
    private String name;
    private Long played;
    private Long won;
    private Long drawn;
    private Long lost;
    private Long scored;
    private Long conceded;
    private Long point;
}
