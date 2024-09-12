package com.webapp.ftm.exception.custom;

import com.webapp.ftm.dto.SeasonDTO;
import com.webapp.ftm.dto.TournamentDTO;
import lombok.Getter;

@Getter
public class InvalidSeasonNameException extends RuntimeException {
    private SeasonDTO seasonDTO;
    public InvalidSeasonNameException(String message,SeasonDTO seasonDTO){
        super(message);
        this.seasonDTO=seasonDTO;
    }
}
