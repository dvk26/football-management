package com.webapp.ftm.exception.custom;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailExistsException extends RuntimeException{
    public EmailExistsException(String message){
        super(message);
    }
}
