package com.webapp.ftm.exception;


import com.webapp.ftm.dto.response.ApiResponseDTO;
import com.webapp.ftm.dto.response.RestResponse;
import com.webapp.ftm.exception.custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(InvalidPlayerListSizeException.class)
    public ResponseEntity<ApiResponseDTO> handleInvalidPlayerListSizeException (InvalidPlayerListSizeException ex){
        return new ResponseEntity<>(new ApiResponseDTO("400","Data Invalid Error: "+ex.getMessage(),ex.getPlayerDTOs()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPlayerAgeException.class)
    public ResponseEntity<ApiResponseDTO> handleInvalidPlayerAgeException (InvalidPlayerAgeException ex){
        return new ResponseEntity<>(new ApiResponseDTO("400","Data Invalid Error: "+ex.getMessage(),ex.getPlayerDTOs()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidForeignPlayerException.class)
    public ResponseEntity<ApiResponseDTO> handleInvalidForeignPlayerException (InvalidForeignPlayerException ex){
        return new ResponseEntity<>(new ApiResponseDTO("400","Data Invalid Error: "+ex.getMessage(),ex.getPlayerDTOs()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRuleScoreException.class)
    public ResponseEntity<ApiResponseDTO> handleInvalidRuleScoreException (InvalidRuleScoreException ex){
        return new ResponseEntity<>(new ApiResponseDTO("400","Data Invalid Error: "+ex.getMessage(),ex.getRuleDTO()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTournamentNameException.class)
    public ResponseEntity<ApiResponseDTO> handleInvalidTournamentName (InvalidTournamentNameException ex){
        return new ResponseEntity<>(new ApiResponseDTO("400","Data Invalid Error: "+ex.getMessage(),ex.getTournamentDTO()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidSeasonNameException.class)
    public ResponseEntity<ApiResponseDTO> handleInvalidSeasonName (InvalidSeasonNameException ex){
        return new ResponseEntity<>(new ApiResponseDTO("400","Data Invalid Error: "+ex.getMessage(),ex.getSeasonDTO()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidSeasonDateException.class)
    public ResponseEntity<ApiResponseDTO> handleInvalidSeasonDate (InvalidSeasonDateException ex){
        return new ResponseEntity<>(new ApiResponseDTO("400","Data Invalid Error: "+ex.getMessage(),ex.getSeasonDTO()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTournamentSeasonException.class)
    public ResponseEntity<ApiResponseDTO> handleInvalidTournamentSeason (InvalidTournamentSeasonException ex){
        return new ResponseEntity<>(new ApiResponseDTO("400","Data Invalid Error: "+ex.getMessage(),ex.getTeamDTO()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTeamNameException.class)
    public ResponseEntity<ApiResponseDTO> handleInvalidTeamName (InvalidTeamNameException ex){
        return new ResponseEntity<>(new ApiResponseDTO("400","Data Invalid Error: "+ex.getMessage(),ex.getTeamDTO()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotQualifiedToScheduleException.class)
    public ResponseEntity<ApiResponseDTO> handleNotQualifiedToSchedule(NotQualifiedToScheduleException ex) {
        return new ResponseEntity<>(new ApiResponseDTO("404",ex.getMessage(),null),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity handleException (EmailExistsException ex) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
