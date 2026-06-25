package com.weather.service;

import com.weather.dto.city.CityResponseDto;

import java.util.List;

public interface CityService {

    List<CityResponseDto> searchCities(String keyword);
}