package com.webapp.ftm.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.webapp.ftm.dto.PlayerDTO;
import com.webapp.ftm.dto.PlayerSearchResponseDTO;
import com.webapp.ftm.dto.request.PlayerSearchRequestDTO;
import com.webapp.ftm.dto.response.ApiResponseDTO;
import com.webapp.ftm.service.PlayerService;
import com.webapp.ftm.service.TeamRegisterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/players")
public class PlayerController {

    private final TeamRegisterService teamRegisterService;
    private final PlayerService playerService;

    @Autowired
    public PlayerController(TeamRegisterService teamRegisterService, PlayerService playerService) {
        this.teamRegisterService = teamRegisterService;
        this.playerService = playerService;
    }

    @PostMapping("/registers")
    public ResponseEntity<ApiResponseDTO> registerPlayers( @RequestBody List< PlayerDTO> playerDTOs){
        teamRegisterService.registerPlayers(playerDTOs);
        return new ResponseEntity<>(new ApiResponseDTO("200","Đăng ký cầu thur thành công!",playerDTOs), HttpStatusCode.valueOf(200));
    }
    @GetMapping("")
    public ResponseEntity<ApiResponseDTO> findPlayers(@ModelAttribute PlayerSearchRequestDTO playerSearchRequestDTO){
        List<PlayerSearchResponseDTO> playerSearchResponses= playerService.findAll(playerSearchRequestDTO);
        return new ResponseEntity<>(new ApiResponseDTO("200","success", playerSearchResponses), HttpStatusCode.valueOf(200));
    }
    @PostMapping("")
    public ResponseEntity<ApiResponseDTO> updatePlayers(@Valid @RequestBody PlayerDTO playerDTO){
        playerService.updatePlayer(playerDTO);
        return new ResponseEntity<>(new ApiResponseDTO("200","success",playerDTO),HttpStatusCode.valueOf(200));
    }
}
