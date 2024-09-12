package com.webapp.ftm.service;

import com.webapp.ftm.dto.RuleDTO;
import com.webapp.ftm.model.RuleEntity;
import org.springframework.stereotype.Service;


public interface RuleService {
    public void saveNewRule(RuleDTO ruleDTO);
    public RuleDTO getNewestRule();
}
