package com.webapp.ftm.dto.response;

import lombok.Builder;

@Builder
public record ApiResponseDTO<T>(String status, String message, T data) {

}
