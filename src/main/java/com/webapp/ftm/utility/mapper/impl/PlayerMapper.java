package com.webapp.ftm.utility.mapper.impl;

import com.webapp.ftm.dto.response.PlayerResponseDTO;
import com.webapp.ftm.model.PlayerEntity;
import com.webapp.ftm.utility.mapper.ResponseMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper implements ResponseMapper<PlayerResponseDTO, PlayerEntity> {
    private final ModelMapper mapper;

    public PlayerMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }
    @Override
    public PlayerResponseDTO mapper(PlayerEntity entity) {
        PlayerResponseDTO result = new PlayerResponseDTO();
        result.setId(entity.getId());
        result.setName(entity.getName());
        return result;
    }
}
