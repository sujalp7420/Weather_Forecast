package com.weather.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDto {

    private String message;
    private String email;
    private String role;
}
