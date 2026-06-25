package com.weather.service;

import com.weather.dto.favorite.FavoriteCityDto;

import java.util.List;

public interface FavoriteCityService {

    List<FavoriteCityDto> getFavorites(Long userId);
}