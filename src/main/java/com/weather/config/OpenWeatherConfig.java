package com.weather.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;

@Getter
@Configuration
public class OpenWeatherConfig {

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String baseUrl;
}
