package com.webapp.ftm.utility.mapper.impl;

import com.webapp.ftm.dto.response.TeamStatisticResponseDTO;
import com.webapp.ftm.model.TeamStatisticEntity;
import com.webapp.ftm.utility.mapper.ResponseMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class TeamStatisticMapper implements ResponseMapper<TeamStatisticResponseDTO, TeamStatisticEntity> {
    private final ModelMapper modelMapper;
    @Autowired
    public TeamStatisticMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TeamStatisticResponseDTO mapper(TeamStatisticEntity entity) {
        TeamStatisticResponseDTO result =  modelMapper.map(entity, TeamStatisticResponseDTO.class);
        result.setName(entity.getTeamStatisticPk().getTeamEntity().getName());
        return result;
    }

    @Override
    public  List<TeamStatisticResponseDTO> listMapper(List<TeamStatisticEntity> entities) {
        List<TeamStatisticResponseDTO> teamStatisticResponseDTOS =  entities.stream().map((TeamStatisticEntity entity) -> mapper(entity)).toList();
        teamStatisticResponseDTOS.forEach((teamStatisticResponseDTO -> {
            teamStatisticResponseDTO.setPlayed((long) ((entities.size()-1)*2));
        }));
        return teamStatisticResponseDTOS;
    }
}
