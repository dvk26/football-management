package com.webapp.ftm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;
@AllArgsConstructor
@Getter
public class MatchRequestDTO {
    private UUID id;
    private Date date;
    private Time time;
}
