package com.webapp.ftm.exception.custom;

import com.webapp.ftm.dto.TournamentDTO;
import com.webapp.ftm.model.TournamentEntity;
import lombok.Getter;

@Getter
public class InvalidTournamentNameException extends RuntimeException {
    private TournamentDTO tournamentDTO;
    public InvalidTournamentNameException(String message,TournamentDTO tournamentDTO){
        super(message);
        this.tournamentDTO=tournamentDTO;
    }
}
