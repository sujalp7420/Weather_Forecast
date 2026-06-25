package com.weather.dto.city;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityResponseDto {

    private Long id;
    private String cityName;
    private String country;
    private Double latitude;
    private Double longitude;
}