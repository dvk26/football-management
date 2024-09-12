package com.webapp.ftm.service;

import com.webapp.ftm.dto.TournamentDTO;
import com.webapp.ftm.dto.response.TeamStatisticResponseDTO;
import com.webapp.ftm.model.TournamentEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public interface TournamentService {
    public TournamentEntity createTournament(TournamentDTO tournamentDTO);
    public UUID findIdByName(String name);
    List<TeamStatisticResponseDTO> ranking(UUID tournementId, UUID seasonId);
    public Map<String,UUID> getTournaments();
    void linking(UUID tournementId, UUID seasonId);
}
