package com.webapp.ftm.controller;

import com.webapp.ftm.dto.SeasonDTO;
import com.webapp.ftm.dto.response.ApiResponseDTO;
import com.webapp.ftm.model.SeasonEntity;
import com.webapp.ftm.service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/seasons")
public class SeasonController {

    @Autowired
    private SeasonService seasonService;

    @PostMapping("")
    public ResponseEntity<ApiResponseDTO> createSeason( @RequestBody SeasonDTO seasonDTO){
        SeasonEntity seasonEntity= seasonService.createSeason(seasonDTO);
        seasonDTO.setId(seasonEntity.getId());
        return new ResponseEntity<>(new ApiResponseDTO("200","message",seasonEntity),HttpStatusCode.valueOf(200));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponseDTO> getAllSeasons(){
        Map<String, UUID> seasons= seasonService.getSeasons();
        return new ResponseEntity<>(new ApiResponseDTO("200","message",seasons),HttpStatusCode.valueOf(200));
    }

}
