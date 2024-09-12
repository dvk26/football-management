package com.webapp.ftm.utility.mapper.impl;

import com.webapp.ftm.dto.response.MatchResponseDTO;
import com.webapp.ftm.model.MatchEntity;
import com.webapp.ftm.model.PlayerStatisticEntity;
import com.webapp.ftm.utility.mapper.ResponseMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MatchMapper implements ResponseMapper<MatchResponseDTO, MatchEntity> {
    private final ModelMapper mapper;
    private final PlayerMapper playerMapper;
    public MatchMapper(ModelMapper mapper, PlayerMapper playerMapper) {
        this.mapper = mapper;
        this.playerMapper = playerMapper;
    }

    @Override
    public MatchResponseDTO mapper(MatchEntity entity) {
        MatchResponseDTO matchResponseDTO = mapper.map(entity,MatchResponseDTO.class);
        matchResponseDTO.setHomeTeamString(entity.getHomeTeam().getName());
        matchResponseDTO.setAwayTeamString(entity.getAwayTeam().getName());
        matchResponseDTO.setVenue(entity.getHomeTeam().getHomeCourt());
        matchResponseDTO.setHomeTeamId(entity.getHomeTeam().getId());
        matchResponseDTO.setAwayTeamId(entity.getAwayTeam().getId());
        matchResponseDTO.setAwayTeamPlayers(playerMapper.listMapper(entity.getAwayTeam().getPlayerStatisticEntities().stream().map((PlayerStatisticEntity res) -> res.getPlayerStatisticPk().getPlayerEntity()).toList()));
        matchResponseDTO.setHomeTeamPlayers(playerMapper.listMapper(entity.getHomeTeam().getPlayerStatisticEntities().stream().map((PlayerStatisticEntity res) -> res.getPlayerStatisticPk().getPlayerEntity()).toList()));

        return matchResponseDTO;
    }
}
