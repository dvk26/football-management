package com.webapp.ftm.controller;

import com.webapp.ftm.dto.request.GoalRequestDTO;
import com.webapp.ftm.dto.request.MatchRequestDTO;
import com.webapp.ftm.dto.response.ApiResponseDTO;
import com.webapp.ftm.repository.GoalRepository;
import com.webapp.ftm.repository.TeamStatisticRepository;
import com.webapp.ftm.service.MatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping(value = "/api/v1/matches")
public class MatchController {
    private final MatchService matchService;
    private final GoalRepository goalRepository;
    private final TeamStatisticRepository teamStatisticRepository;

    public MatchController(MatchService matchService, GoalRepository goalRepository, TeamStatisticRepository teamStatisticRepository) {
        this.matchService = matchService;
        this.goalRepository = goalRepository;
        this.teamStatisticRepository = teamStatisticRepository;
    }

    @GetMapping
    public ApiResponseDTO getMatchesInTournament(@RequestParam("season_id") UUID seasonId, @RequestParam("tournament_id")UUID tournamentId) {
        return ApiResponseDTO.builder()
                .status("200")
                .message("All matches in tournament")
                .data(matchService.getMatches(tournamentId,seasonId))
                .build();
    }

    @PostMapping
    public ApiResponseDTO updateMatches(MatchRequestDTO matchRequestDTO) {
        return ApiResponseDTO.builder()
                .status("200")
                .message("update matches in tournament")
                .data(matchService.updateMatch(matchRequestDTO))
                .build();
    }

    @PostMapping("/goal")
    public ApiResponseDTO updateGoal(@RequestBody List<GoalRequestDTO> goalRequestDTOs) {
        goalRequestDTOs.forEach(matchService::saveGoal);
        return ApiResponseDTO.builder()
                .status("200")
                .message("All matches in tournament")
                .data(Boolean.TRUE)
                .build();
    }

    @GetMapping("/goal/conceded")
    public ApiResponseDTO updateConcededGoal(@RequestParam("season_id") UUID seasonId, @RequestParam("tournament_id")UUID tournamentId, @RequestParam("team_id")UUID teamId) {
        matchService.updateConcededGoal(tournamentId,seasonId,teamId);
        return ApiResponseDTO.builder()
                .status("200")
                .message("Update conceded goal")
                .data(Boolean.TRUE)
                .build();
    }
}
