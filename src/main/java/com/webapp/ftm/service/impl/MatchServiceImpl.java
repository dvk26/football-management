package com.webapp.ftm.service.impl;

import com.webapp.ftm.dto.request.GoalRequestDTO;
import com.webapp.ftm.dto.request.MatchRequestDTO;
import com.webapp.ftm.dto.response.MatchResponseDTO;
import com.webapp.ftm.model.MatchEntity;
import com.webapp.ftm.model.TeamStatisticEntity;
import com.webapp.ftm.repository.GoalRepository;
import com.webapp.ftm.repository.MatchesRepository;
import com.webapp.ftm.repository.TeamStatisticRepository;
import com.webapp.ftm.service.MatchService;
import com.webapp.ftm.utility.mapper.impl.MatchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class MatchServiceImpl implements MatchService {
    private final MatchesRepository matchesRepository;
    private final MatchMapper matchMapper;
    private final GoalRepository goalRepository;
    private final TeamStatisticRepository teamStatisticRepository;
    @Autowired
    public MatchServiceImpl(MatchesRepository matchesRepository, MatchMapper matchMapper, GoalRepository goalRepository, TeamStatisticRepository teamStatisticRepository) {
        this.matchesRepository = matchesRepository;
        this.matchMapper = matchMapper;
        this.goalRepository = goalRepository;
        this.teamStatisticRepository = teamStatisticRepository;
    }

    @Override
    public boolean updateMatch(MatchRequestDTO matchRequestDTO) {
        MatchEntity matchEntity = matchesRepository.findById(matchRequestDTO.getId()).get();
        matchEntity.setDate(matchRequestDTO.getDate());
        matchEntity.setTime(matchRequestDTO.getTime());
        matchesRepository.save(matchEntity);
        return true;
    }

    @Override
    public void saveGoal(GoalRequestDTO goalRequestDTO) {
        goalRepository.saveGoal(goalRequestDTO.getMatchId(),goalRequestDTO.getPlayerId(),goalRequestDTO.getTeamId(),goalRequestDTO.getGoalType(),goalRequestDTO.getTimeScore());
    }

    @Override
    public List<MatchResponseDTO> getMatches(UUID tournamentId, UUID seasonId) {
        List<MatchEntity> matches = matchesRepository.findAllBy_TournamentId_And_SeasonId(tournamentId,seasonId);
        return matchMapper.listMapper(matches.stream().sorted(Comparator.comparingLong(MatchEntity::getRound)).toList());
    }

    @Override
    public void updateConcededGoal(UUID tournamentId, UUID seasonId, UUID teamId) {
        TeamStatisticEntity teamStatisticEntity = teamStatisticRepository.findAllBy_TournamentId_And_SeasonId_And_TeamId(tournamentId,seasonId,teamId);
        teamStatisticEntity.setConceded(teamStatisticEntity.getConceded() + 1);
        teamStatisticRepository.save(teamStatisticEntity);
    }

}
