package com.webapp.ftm.dto;

import com.webapp.ftm.enums.RankingOrderDetail;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RuleDTO {
    private UUID id;
    private Long minAge;
    private Long maxAge;
    private Long minNumOfPlayers;
    private Long maxNumOfPlayers;
    private Long maxNumOfForeigners;
    private Long maxTimeToScore;
    private Long numOfTypeScore;
    private Long winScore;
    private Long loseScore;
    private Long drawnScore;
    @Enumerated(EnumType.STRING)
    private RankingOrderDetail rankingOrderDetail;
}
