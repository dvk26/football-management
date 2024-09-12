package com.webapp.ftm.service;

import com.webapp.ftm.dto.request.GoalRequestDTO;
import com.webapp.ftm.dto.request.MatchRequestDTO;
import com.webapp.ftm.dto.response.MatchResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface MatchService {
    boolean updateMatch(MatchRequestDTO matchRequestDTO);
    void saveGoal(GoalRequestDTO goalRequestDTO);
    List<MatchResponseDTO> getMatches(UUID tournamentId, UUID seasonId);
    void updateConcededGoal(UUID tournamentId, UUID seasonId, UUID teamId);
}
