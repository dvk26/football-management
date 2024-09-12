package com.webapp.ftm.exception.custom;

import com.webapp.ftm.dto.PlayerDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class InvalidForeignPlayerException extends RuntimeException {
    private List<PlayerDTO> playerDTOs;
    public InvalidForeignPlayerException(String message,List<PlayerDTO> playerDTOs) {
        super(message);
        this.playerDTOs=playerDTOs;
    }
}
