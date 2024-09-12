package com.webapp.ftm.exception.custom;

import com.webapp.ftm.dto.PlayerDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class InvalidPlayerAgeException extends RuntimeException{
    private List<PlayerDTO> playerDTOs;
    public InvalidPlayerAgeException(String message,List<PlayerDTO> playerDTOs) {
        super(message);
        this.playerDTOs=playerDTOs;
    }
}
