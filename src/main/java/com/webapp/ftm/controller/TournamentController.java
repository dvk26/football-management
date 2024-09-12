package com.webapp.ftm.controller;


import com.webapp.ftm.dto.TournamentDTO;
import com.webapp.ftm.dto.response.ApiResponseDTO;
import com.webapp.ftm.model.TournamentEntity;
import com.webapp.ftm.service.MatchService;
import com.webapp.ftm.service.TournamentScheduleService;
import com.webapp.ftm.service.TournamentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tournaments")
public class TournamentController {
    private final TournamentService tournamentService;
    private final TournamentScheduleService tournamentScheduleService;
    private final MatchService matchService;

    public TournamentController(TournamentService tournamentService, TournamentScheduleService tournamentScheduleService, MatchService matchService) {
        this.tournamentService = tournamentService;
        this.tournamentScheduleService = tournamentScheduleService;
        this.matchService = matchService;
    }

    @PostMapping("")
    public ResponseEntity<ApiResponseDTO> createTournament(@Valid @RequestBody TournamentDTO tournamentDTO){
        TournamentEntity tournamentEntity = tournamentService.createTournament(tournamentDTO);
        tournamentDTO.setId(tournamentEntity.getId());
        return new ResponseEntity<>(new ApiResponseDTO<>("200","success",tournamentDTO), HttpStatusCode.valueOf(200));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponseDTO> getAllTournaments(){
        Map<String, UUID> tournaments= tournamentService.getTournaments();
        return new ResponseEntity<>(new ApiResponseDTO<>("200","success",tournaments), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/schedule")
    public ApiResponseDTO scheduler(@RequestParam("season_id")UUID seasonId, @RequestParam("tournament_id")UUID tournamentId) {
        return ApiResponseDTO.builder()
                .status("200")
                .message("Schedule matches succeed!")
                .data(tournamentScheduleService.schedule(seasonId,tournamentId))
                .build();
    }
    @GetMapping("/ranking")
    public ApiResponseDTO ranking(@RequestParam("season_id")UUID seasonId, @RequestParam("tournament_id")UUID tournamentId) {
        return ApiResponseDTO.builder()
                .status("200")
                .message("ranking for this tournament")
                .data(tournamentService.ranking(tournamentId,seasonId))
                .build();
    }

    @GetMapping("/linking")
    public ApiResponseDTO linking(@RequestParam("season_id")UUID seasonId, @RequestParam("tournament_id")UUID tournamentId) {
        tournamentService.linking(tournamentId, seasonId);
        return ApiResponseDTO.builder()
                .status("200")
                .message("linking for this tournament")
                .data(Boolean.TRUE)
                .build();
    }
}
