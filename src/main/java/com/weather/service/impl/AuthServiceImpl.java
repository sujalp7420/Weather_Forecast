package com.weather.service.impl;

import com.weather.dto.auth.*;
import com.weather.entity.Role;
import com.weather.entity.User;
import com.weather.exception.InvalidCredentialsException;
import com.weather.exception.UserAlreadyExistsException;
import com.weather.repository.RoleRepository;
import com.weather.repository.UserRepository;
import com.weather.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDto register(RegisterRequestDto request) {

        if(userRepository.existsByEmail(request.getEmail())){
            throw new UserAlreadyExistsException(
                    "Email already registered");
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow();

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .roles(Set.of(userRole))
                .build();

        userRepository.save(user);

        return AuthResponseDto.builder()
                .message("Registration Successful")
                .email(user.getEmail())
                .role("USER")
                .build();
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if(!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new InvalidCredentialsException(
                    "Invalid Email or Password");
        }

        return AuthResponseDto.builder()
                .message("Login Successful")
                .email(user.getEmail())
                .role("USER")
                .build();
    }
}
