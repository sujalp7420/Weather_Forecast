package com.weather.mapper;

import com.weather.dto.user.UserResponseDto;
import com.weather.entity.User;

public class UserMapper {

    public static UserResponseDto toDto(User user) {

        return UserResponseDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .build();
    }
}