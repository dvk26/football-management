package com.webapp.ftm.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Setter
@Getter
public class PlayerResponseDTO {
    private UUID id;
    private String name;
}
