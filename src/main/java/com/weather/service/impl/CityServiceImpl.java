package com.weather.service.impl;

import com.weather.dto.city.CityResponseDto;
import com.weather.repository.CityRepository;
import com.weather.service.CityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public List<CityResponseDto> searchCities(String keyword) {

        return cityRepository
                .findByCityNameContainingIgnoreCase(keyword)
                .stream()
                .map(city -> CityResponseDto.builder()
                        .id(city.getId())
                        .cityName(city.getCityName())
                        .country(city.getCountry())
                        .latitude(city.getLatitude())
                        .longitude(city.getLongitude())
                        .build())
                .toList();
    }
}