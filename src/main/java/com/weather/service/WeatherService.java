package com.weather.service;

import com.weather.dto.weather.AnalyticsPointDto;
import com.weather.dto.weather.ForecastDto;
import com.weather.dto.weather.WeatherResponseDto;

import java.util.List;

public interface WeatherService {

    WeatherResponseDto getCurrentWeather(String city);

    List<ForecastDto> getHourlyForecast(String city);

    List<ForecastDto> getDailyForecast(String city);

    List<AnalyticsPointDto> getAnalytics(String city);
}
