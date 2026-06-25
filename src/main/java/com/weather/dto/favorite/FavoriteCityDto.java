package com.weather.dto.favorite;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FavoriteCityDto {

    private Long cityId;

    private String cityName;

    private String country;
}