package com.webapp.ftm.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public interface TeamService {
    public Map<String, UUID> getTeams();
}
