package com.weather.service;

import com.weather.dto.auth.AuthResponseDto;
import com.weather.dto.auth.LoginRequestDto;
import com.weather.dto.auth.RegisterRequestDto;

public interface AuthService {

    AuthResponseDto register(RegisterRequestDto request);

    AuthResponseDto login(LoginRequestDto request);
}
