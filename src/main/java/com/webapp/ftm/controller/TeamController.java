package com.webapp.ftm.controller;

import com.webapp.ftm.dto.response.ApiResponseDTO;
import com.webapp.ftm.dto.TeamDTO;
import com.webapp.ftm.model.TeamStatisticEntity;
import com.webapp.ftm.service.TeamRegisterService;
import com.webapp.ftm.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {

    @Autowired
    private TeamRegisterService teamRegisterService;
    @Autowired
    private TeamService teamService;

    @PostMapping("/registers")
    public ResponseEntity<ApiResponseDTO> registerTeam(@Valid @RequestBody TeamDTO teamDTO){
        TeamStatisticEntity registeredTeamStatistic = teamRegisterService.registerTeam(teamDTO);
        teamDTO.setId(registeredTeamStatistic.getTeamStatisticPk().getTeamEntity().getId());
        return new ResponseEntity<>(new ApiResponseDTO("200","success",teamDTO), HttpStatusCode.valueOf(200));
    }
    @GetMapping("")
    public ResponseEntity<ApiResponseDTO> getAllTeams(){
        Map<String, UUID> teams= teamService.getTeams();
        return new ResponseEntity<>(new ApiResponseDTO("200","success",teams), HttpStatusCode.valueOf(200));
    }
}
