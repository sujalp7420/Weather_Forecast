package com.weather.dto.weather;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherResponseDto {

    private String city;

    private Double temperature;

    private Double feelsLike;

    private Integer humidity;

    private Double pressure;

    private Integer visibilityMeters;

    private Double windSpeed;

    private Double uvIndex;

    private String sunrise;

    private String sunset;

    private String condition;

    private String icon;

    private WeatherSummaryDto aiSummary;
}
