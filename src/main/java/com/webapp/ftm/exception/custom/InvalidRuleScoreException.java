package com.webapp.ftm.exception.custom;

import com.webapp.ftm.dto.RuleDTO;
import lombok.Getter;

@Getter
public class InvalidRuleScoreException extends RuntimeException{
    private RuleDTO ruleDTO;
    public InvalidRuleScoreException(String message,RuleDTO ruleDTO){
        super(message);
        this.ruleDTO=ruleDTO;
    }
}
