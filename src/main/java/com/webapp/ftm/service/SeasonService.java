package com.webapp.ftm.service;

import com.webapp.ftm.dto.SeasonDTO;
import com.webapp.ftm.model.SeasonEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public interface SeasonService {
    public SeasonEntity createSeason(SeasonDTO seasonDTO);
    public Map<String,UUID> getSeasons();

}
