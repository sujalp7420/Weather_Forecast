package com.weather.dto.weather;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnalyticsPointDto {

    private String label;

    private Double temperature;

    private Integer humidity;

    private Double rainfall;

    private Double windSpeed;
}
