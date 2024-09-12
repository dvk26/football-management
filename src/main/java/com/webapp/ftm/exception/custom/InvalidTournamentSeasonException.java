package com.webapp.ftm.exception.custom;

import com.webapp.ftm.dto.TeamDTO;
import lombok.Getter;

@Getter
public class InvalidTournamentSeasonException extends RuntimeException{
    private TeamDTO teamDTO;
    public InvalidTournamentSeasonException(String message, TeamDTO teamDTO){
        super(message);
        this.teamDTO=teamDTO;
    }
}
