package com.webapp.ftm.utility.mapper.impl;

import com.webapp.ftm.dto.PlayerSearchResponseDTO;

import com.webapp.ftm.model.PlayerEntity;
import com.webapp.ftm.model.PlayerStatisticEntity;
import com.webapp.ftm.utility.mapper.ResponseMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlayerSearchResponseMapper implements ResponseMapper<PlayerSearchResponseDTO, PlayerEntity> {

    private final ModelMapper modelMapper;

    @Autowired
    public PlayerSearchResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public PlayerSearchResponseDTO mapper(PlayerEntity entity) {
        PlayerSearchResponseDTO playerSearchResponseDTO= modelMapper.map(entity,PlayerSearchResponseDTO.class);
        //Get all statistics of player
        List<PlayerStatisticEntity> playerStatistics=entity.getPlayerStatistics();
        //Count total goals of player
        Long totalGoals=playerStatistics.stream().mapToLong(PlayerStatisticEntity::getGoalScore).sum();
        //Convert all tournamentSeasons to string
        String tournamentSeasons= playerStatistics.stream()
                .map(s->
                        s.getPlayerStatisticPk().getTournamentSeasonEntity().getTournamentSeasonPk().getTournamentEntity().getName()
                                + "-" +
                                s.getPlayerStatisticPk().getTournamentSeasonEntity().getTournamentSeasonPk().getSeasonEntity().getName()).collect(Collectors.joining(", "));
        playerSearchResponseDTO.setTotalGoals(totalGoals);
        playerSearchResponseDTO.setTournamentSeasons(tournamentSeasons);
        playerSearchResponseDTO.setTeam(entity.getPlayerStatistics().get(0).getPlayerStatisticPk().getTeamEntity().getName());
        return playerSearchResponseDTO;
    }
}
