package com.webapp.ftm.enums;

public enum TournamentStatus {
    BEGINSOON("BEGINSOON"),
    PROGRESS("PROGRESS"),
    END("END"),
    POSTPONE("POSTPONE");
    private String status;

     TournamentStatus(String status) {
        this.status = status;
    }
}
