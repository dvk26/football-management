package com.webapp.ftm.service;

import com.webapp.ftm.dto.response.MatchResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface TournamentScheduleService {
    List<MatchResponseDTO> schedule(UUID seasonId, UUID tournamentId);
}
