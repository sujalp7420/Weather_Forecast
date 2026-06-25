package com.weather.service.impl;

import com.weather.dto.favorite.FavoriteCityDto;
import com.weather.repository.FavoriteCityRepository;
import com.weather.service.FavoriteCityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteCityServiceImpl
        implements FavoriteCityService {

    private final FavoriteCityRepository favoriteCityRepository;

    @Override
    public List<FavoriteCityDto> getFavorites(Long userId) {

        return favoriteCityRepository.findAll()
                .stream()
                .filter(f -> f.getUser().getId().equals(userId))
                .map(f -> FavoriteCityDto.builder()
                        .cityId(f.getCity().getId())
                        .cityName(f.getCity().getCityName())
                        .country(f.getCity().getCountry())
                        .build())
                .toList();
    }
}