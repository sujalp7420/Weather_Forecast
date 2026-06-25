package com.weather.dto.weather;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherSummaryDto {

    private String city;

    private String aiSummary;

    private String travelAdvice;

    private String clothingAdvice;

    private String healthAdvice;
}