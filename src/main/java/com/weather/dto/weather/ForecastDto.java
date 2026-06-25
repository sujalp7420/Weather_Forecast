package com.weather.dto.weather;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ForecastDto {

    private String date;

    private String time;

    private Double temperature;

    private Double minTemp;

    private Double maxTemp;

    private Integer humidity;

    private Double windSpeed;

    private String condition;

    private String icon;
}
