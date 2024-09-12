package com.webapp.ftm.exception.custom;

import com.webapp.ftm.dto.TeamDTO;
import lombok.Getter;

@Getter
public class InvalidTeamNameException extends RuntimeException{
    private TeamDTO teamDTO;
    public InvalidTeamNameException(String message, TeamDTO teamDTO){
        super(message);
        this.teamDTO=teamDTO;
    }
}
