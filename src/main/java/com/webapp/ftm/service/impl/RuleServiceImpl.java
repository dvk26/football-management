package com.webapp.ftm.service.impl;

import com.webapp.ftm.dto.RuleDTO;
import com.webapp.ftm.exception.custom.InvalidRuleScoreException;
import com.webapp.ftm.model.RuleEntity;
import com.webapp.ftm.repository.RuleRepository;
import com.webapp.ftm.service.RuleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RuleRepository ruleRepository;
    @Override
    public void saveNewRule(RuleDTO ruleDTO) {
        // check the winscore > drawnscore > losescore
        if(!(ruleDTO.getWinScore()>ruleDTO.getDrawnScore())||!(ruleDTO.getDrawnScore()>ruleDTO.getLoseScore())){
            throw new InvalidRuleScoreException( "Người dùng phải nhập đúng: điểm thắng > điểm hòa > điểm thua.",ruleDTO);
        }
        //use model mapper to convert ruleDTO to ruleEntity
        RuleEntity ruleEntity = modelMapper.map(ruleDTO, RuleEntity.class);
        ruleEntity.setId(null);
        ruleEntity.setTimeCreated(LocalDateTime.now());
        ruleRepository.save(ruleEntity);
    }
    @Override
    public RuleDTO getNewestRule() {
        //Get the newest rule
        RuleEntity ruleEntity= ruleRepository.findAllByOrderByTimeCreatedDesc().get(0);

        RuleDTO ruleDTO=modelMapper.map(ruleEntity,RuleDTO.class);

        return ruleDTO;
    }
}
