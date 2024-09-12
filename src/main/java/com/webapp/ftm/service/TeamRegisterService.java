package com.webapp.ftm.service;

import com.webapp.ftm.dto.PlayerDTO;
import com.webapp.ftm.dto.TeamDTO;
import com.webapp.ftm.model.PlayerStatisticEntity;
import com.webapp.ftm.model.TeamStatisticEntity;
import com.webapp.ftm.model.pk.TeamStatisticPk;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeamRegisterService {
    public TeamStatisticEntity registerTeam(TeamDTO teamDTO);
    public void registerPlayers(List<PlayerDTO> playerDTOS);
}
