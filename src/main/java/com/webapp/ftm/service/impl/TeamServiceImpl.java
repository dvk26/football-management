package com.webapp.ftm.service.impl;

import com.webapp.ftm.model.TeamEntity;
import com.webapp.ftm.repository.TeamRepository;
import com.webapp.ftm.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public Map<String, UUID> getTeams() {
        Map<String,UUID> res=new HashMap<>();
        List<TeamEntity> teamEntities= teamRepository.findAll();
        Map<String,UUID> teamMap= new TreeMap<>();
        for(TeamEntity teamEntity:teamEntities){
            teamMap.put(teamEntity.getName(),teamEntity.getId());
        }
        return teamMap;
    }
}
