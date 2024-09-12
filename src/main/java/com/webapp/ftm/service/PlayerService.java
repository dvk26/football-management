package com.webapp.ftm.service;

import com.webapp.ftm.dto.PlayerDTO;
import com.webapp.ftm.dto.request.PlayerSearchRequestDTO;
import com.webapp.ftm.dto.PlayerSearchResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlayerService {
    List<PlayerSearchResponseDTO> findAll(PlayerSearchRequestDTO playerSearchRequestDTO);
    void updatePlayer(PlayerDTO playerDTO);
}
