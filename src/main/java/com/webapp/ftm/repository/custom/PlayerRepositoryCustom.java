package com.webapp.ftm.repository.custom;

import com.webapp.ftm.dto.request.PlayerSearchRequestDTO;
import com.webapp.ftm.model.PlayerEntity;

import java.util.List;

public interface PlayerRepositoryCustom {

    public List<PlayerEntity> findAllPlayers(PlayerSearchRequestDTO playerSearchRequestDTO);
}
