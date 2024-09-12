package com.webapp.ftm.exception.custom;

import com.webapp.ftm.dto.SeasonDTO;
import com.webapp.ftm.model.SeasonEntity;
import lombok.Getter;

@Getter
public class InvalidSeasonDateException extends RuntimeException{
    private SeasonDTO seasonDTO;

    public InvalidSeasonDateException(String message,SeasonDTO seasonDTO){
        super(message);
        this.seasonDTO=seasonDTO;
    }

}
