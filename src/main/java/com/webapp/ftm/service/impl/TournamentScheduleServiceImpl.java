package com.webapp.ftm.service.impl;

import com.webapp.ftm.dto.response.MatchResponseDTO;
import com.webapp.ftm.enums.TournamentStatus;
import com.webapp.ftm.exception.custom.NotQualifiedToScheduleException;
import com.webapp.ftm.model.MatchEntity;
import com.webapp.ftm.model.TeamEntity;
import com.webapp.ftm.model.TournamentSeasonEntity;
import com.webapp.ftm.repository.MatchesRepository;
import com.webapp.ftm.repository.TeamRepository;
import com.webapp.ftm.repository.TournamentSeasonRepository;
import com.webapp.ftm.service.TournamentScheduleService;
import com.webapp.ftm.utility.mapper.impl.MatchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class TournamentScheduleServiceImpl implements TournamentScheduleService {
    private final TeamRepository teamRepository;
    private final MatchesRepository matchesRepository;
    private final TournamentSeasonRepository tournamentSeasonRepository;
    private final MatchMapper matchMapper ;
    @Autowired
    public TournamentScheduleServiceImpl(TeamRepository teamRepository, MatchesRepository matchesRepository, TournamentSeasonRepository tournamentSeasonRepository, MatchMapper matchMapper) {
        this.teamRepository = teamRepository;
        this.matchesRepository = matchesRepository;
        this.tournamentSeasonRepository = tournamentSeasonRepository;
        this.matchMapper = matchMapper;
    }

    @Override
    public List<MatchResponseDTO> schedule(UUID seasonId, UUID tournamentId) {
        // check qualified to schedule
        List<TeamEntity> teamEntities= teamRepository.findAllByTournamentAndSeason(tournamentId,seasonId);
        if (teamEntities == null || teamEntities.size() < 2) {
            throw new NotQualifiedToScheduleException();
        }
        //Set status for tournament
        TournamentSeasonEntity tournamentSeasonEntity = tournamentSeasonRepository.findByTournamentSeasonPk_TournamentEntity_IdAndTournamentSeasonPk_SeasonEntity_Id(tournamentId,seasonId).get();
        tournamentSeasonEntity.setStatus(TournamentStatus.PROGRESS);
        List<MatchResponseDTO> matches = roundRobinSchedule(teamEntities,tournamentSeasonEntity);
        matches.stream().sorted(Comparator.comparingLong(MatchResponseDTO::getRound));
        return matches;
    }

    private List<MatchResponseDTO> roundRobinSchedule(List<TeamEntity> teamEntities, TournamentSeasonEntity tournamentSeasonEntity) {
        List<List<Pair<Integer,Integer>>> rounds = getMatchForEachRound(teamEntities.size());
        List<MatchEntity> matchEntities = new ArrayList<>();
        int roundIndex = 0;
        for (List<Pair<Integer,Integer>> round : rounds) {
            roundIndex ++;
            for (Pair<Integer,Integer> match : round) {
                if (match.getFirst() > teamEntities.size() || match.getSecond() > teamEntities.size()) continue;
                MatchEntity matchEntity = MatchEntity.builder()
                        .homeTeam(teamEntities.get(match.getFirst()-1))
                        .awayTeam(teamEntities.get(match.getSecond()-1))
                        .round(Long.valueOf(roundIndex))
                        .team1Score(0l)
                        .team2Score(0l)
                        .tournamentSeasonEntity(tournamentSeasonEntity)
                        .build();
                MatchEntity matchEntityAway = MatchEntity.builder()
                        .homeTeam(teamEntities.get(match.getSecond()-1))
                        .awayTeam(teamEntities.get(match.getFirst()-1))
                        .round(Long.valueOf(roundIndex + teamEntities.size() - 1))
                        .team1Score(0l)
                        .team2Score(0l)
                        .tournamentSeasonEntity(tournamentSeasonEntity)
                        .build();
                matchEntities.add(matchEntity);
                matchEntities.add(matchEntityAway);
            }
        }
        return matchMapper.listMapper(matchesRepository.saveAll(matchEntities));
    }

    private List<List<Pair<Integer,Integer>>> getMatchForEachRound(int totalOfTeam) {
        if (totalOfTeam % 2 == 1) totalOfTeam += 1;
        List<List<Pair<Integer,Integer>>> result = new ArrayList<>();
        int[][] initRound = new int[2][totalOfTeam / 2];
        //init round first
        for (int i = 0 ; i < totalOfTeam / 2; i++) {initRound[0][i] = i + 1;
            initRound[1][i] = totalOfTeam - i;
        }
        for (int round = 1 ; round < totalOfTeam; round++) {
            List<Pair<Integer,Integer>> roundMatch = new ArrayList<>();
            for (int i = 0; i < totalOfTeam / 2; i++) {
                roundMatch.add(Pair.of(Integer.valueOf(initRound[0][i]),Integer.valueOf(initRound[1][i])));
                initRound[0][i] =fixRange(initRound[0][i] + (totalOfTeam / 2), totalOfTeam);
                initRound[1][i] = fixRange(initRound[1][i] + (totalOfTeam / 2), totalOfTeam);
                if (i == 0) {
                    //swap home and away team for first match
                    int temp = initRound[0][i];
                    initRound[0][i] = initRound[1][i];
                    initRound[1][i] = temp;
                    if (round % 2 == 0) {
                        initRound[1][i] = totalOfTeam;
                    } else {
                        initRound[0][i] = totalOfTeam;
                    }
                }
            }
            result.add(roundMatch);
        }
        return result;
    }
    private int fixRange(int value, int totalOfTeam) {
        if (value > totalOfTeam - 1) {
            value = value % (totalOfTeam - 1);
        }
        return value;
    }

}
