package com.weather.service.impl;

import com.weather.dto.user.UserResponseDto;
import com.weather.entity.User;
import com.weather.mapper.UserMapper;
import com.weather.repository.UserRepository;
import com.weather.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDto getUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow();

        return UserMapper.toDto(user);
    }
}