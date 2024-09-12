package com.webapp.ftm.enums;

public enum GoalType {
    GOAL(1L), PENALTY(2L), OG(3L);

    private final Long goalType;

    GoalType(Long goalType) {
        this.goalType = goalType;
    }

    public Long getValue() {
        return goalType;
    }

}