package com.webapp.ftm.service.impl;


import com.webapp.ftm.dto.TournamentDTO;
import com.webapp.ftm.dto.response.TeamStatisticResponseDTO;
import com.webapp.ftm.enums.RankingOrderDetail;
import com.webapp.ftm.enums.TournamentStatus;
import com.webapp.ftm.exception.custom.InvalidTournamentNameException;
import com.webapp.ftm.model.RuleEntity;
import com.webapp.ftm.model.TeamStatisticEntity;
import com.webapp.ftm.model.TournamentEntity;
import com.webapp.ftm.model.TournamentSeasonEntity;
import com.webapp.ftm.model.pk.TournamentSeasonPk;
import com.webapp.ftm.repository.*;
import com.webapp.ftm.service.TournamentService;
import com.webapp.ftm.utility.mapper.impl.MatchMapper;
import com.webapp.ftm.utility.mapper.impl.TeamStatisticMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TournamentServiceImpl implements TournamentService {


    private final TournamentRepository tournamentRepository;
    private final TeamStatisticRepository teamStatisticRepository;
    private final RuleRepository ruleRepository;
    private final TeamStatisticMapper  teamStatisticMapper;
    private final MatchMapper matchMapper;
    private final TournamentSeasonRepository tournamentSeasonRepository;
    private final MatchesRepository matchesRepository;
    private final SeasonRepository seasonRepository;
    @Autowired
    public TournamentServiceImpl(TournamentRepository tournamentRepository, TeamStatisticRepository teamStatisticRepository, RuleRepository ruleRepository, TeamStatisticMapper teamStatisticMapper, MatchMapper matchMapper, TournamentSeasonRepository tournamentSeasonRepository, MatchesRepository matchesRepository, SeasonRepository seasonRepository) {
        this.tournamentRepository = tournamentRepository;
        this.teamStatisticRepository = teamStatisticRepository;
        this.ruleRepository = ruleRepository;
        this.teamStatisticMapper = teamStatisticMapper;
        this.matchMapper = matchMapper;
        this.tournamentSeasonRepository = tournamentSeasonRepository;
        this.matchesRepository = matchesRepository;
        this.seasonRepository = seasonRepository;
    }


    @Override
    public TournamentEntity createTournament(TournamentDTO tournamentDTO) {
        List<TournamentEntity> tournamentList= tournamentRepository.findByName(tournamentDTO.getName());
        if(tournamentList.size()>0) throw new InvalidTournamentNameException("Tên mùa giải đã tồn tại!",tournamentDTO);
        TournamentEntity tournamentEntity= new TournamentEntity();
        tournamentEntity.setName(tournamentDTO.getName());
        return tournamentRepository.save(tournamentEntity);
    }

    @Override
    public UUID findIdByName(String name) {
        TournamentEntity tournamentEntity= tournamentRepository.findByName(name).get(0);
        return tournamentEntity.getId();
    }
    @Override
    public List<TeamStatisticResponseDTO> ranking(UUID tournementId, UUID seasonId) {
        List<TeamStatisticEntity> teamStatisticEntities = teamStatisticRepository.findAllBy_TournamentId_And_SeasonId(tournementId,seasonId);
        // calculate point
        RuleEntity thisRule = tournamentSeasonRepository.findByTournamentSeasonPk_TournamentEntity_IdAndTournamentSeasonPk_SeasonEntity_Id(tournementId,seasonId).get().getRule();
        teamStatisticEntities.forEach((TeamStatisticEntity teamstatisticEntity) -> {
            teamstatisticEntity.setPoint(teamstatisticEntity.getWon() * thisRule.getWinScore() +
                    teamstatisticEntity.getDrawn() * thisRule.getDrawnScore() +
                    teamstatisticEntity.getLost() * thisRule.getLoseScore());});
           return teamStatisticMapper.listMapper(thisRule.getRankingOrderDetail() == RankingOrderDetail.DEFAULT ? defaultOrder(teamStatisticEntities) : alternativeOrder(teamStatisticEntities));
    }

    @Override
    public Map<String, UUID> getTournaments() {
        Map<String ,UUID> map= new TreeMap<>();
        List<TournamentEntity> tournamentEntities= tournamentRepository.findAll();
        for(TournamentEntity tournamentEntity: tournamentEntities){
            map.put(tournamentEntity.getName(),tournamentEntity.getId());
        }
        return map;
    }

    @Override
    public void linking(UUID tournementId, UUID seasonId) {
        TournamentSeasonEntity newLeauge = new TournamentSeasonEntity();
        newLeauge.setStatus(TournamentStatus.PROGRESS);
        newLeauge.setRule(ruleRepository.findAllByOrderByTimeCreatedDesc().get(0));
        newLeauge.setTournamentSeasonPk(new TournamentSeasonPk(tournamentRepository.findById(tournementId).get(),seasonRepository.findById(seasonId).get()));
        tournamentSeasonRepository.save(newLeauge);
    }

    private int getMatchesWinningAgainst(TeamStatisticEntity team, TeamStatisticEntity againstTeam) {
        TournamentSeasonEntity tournamentSeasonEntity = team.getTeamStatisticPk().getTournamentSeasonEntity();
        return matchesRepository.countMatchesWinningAgainst(team.getTeamStatisticPk().getTeamEntity().getId(),againstTeam.getTeamStatisticPk().getTeamEntity().getId(),tournamentSeasonEntity.getTournamentSeasonPk().getTournamentEntity().getId(),tournamentSeasonEntity.getTournamentSeasonPk().getSeasonEntity().getId());
    }

    private List<TeamStatisticEntity> defaultOrder(List<TeamStatisticEntity> teamStatisticEntities) {
        return teamStatisticEntities.parallelStream()
                .sorted(Comparator.comparing(TeamStatisticEntity::getPoint)
                        .thenComparing(TeamStatisticEntity::getGd)
                        .thenComparing(TeamStatisticEntity::getScored)
                        .thenComparing((a,b) -> getMatchesWinningAgainst(a,b)).reversed())
                .collect(Collectors.toList());
    }

    private List<TeamStatisticEntity> alternativeOrder(List<TeamStatisticEntity> teamStatisticEntities) {
        return teamStatisticEntities.parallelStream()
                .sorted(Comparator.comparing(TeamStatisticEntity::getPoint)
                        .thenComparing((a,b) -> getMatchesWinningAgainst(a,b))
                        .thenComparing(TeamStatisticEntity::getGd)
                        .thenComparing(TeamStatisticEntity::getScored)
                        .reversed())
                .collect(Collectors.toList());
    }
}
