package com.weather.service;

import com.weather.dto.user.UserResponseDto;

public interface UserService {

    UserResponseDto getUser(Long id);
}