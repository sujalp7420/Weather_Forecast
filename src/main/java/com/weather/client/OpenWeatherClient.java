package com.weather.client;

import com.weather.config.OpenWeatherConfig;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OpenWeatherClient {

    private final OpenWeatherConfig config;

    private final RestClient restClient =
            RestClient.create();

    public Map<String, Object> getCurrentWeather(
            String city) {

        if (config.getApiKey() == null || config.getApiKey().isBlank()) {
            return demoCurrentWeather(city);
        }

        String url =
                UriComponentsBuilder.fromHttpUrl(config.getBaseUrl())
                        .path("/weather")
                        .queryParam("q", city)
                        .queryParam("appid", config.getApiKey())
                        .queryParam("units", "metric")
                        .build()
                        .toUriString();

        return restClient.get()
                .uri(url)
                .retrieve()
                .body(Map.class);
    }

    public Map<String, Object> getForecast(String city) {
        if (config.getApiKey() == null || config.getApiKey().isBlank()) {
            return demoForecast();
        }

        String url = UriComponentsBuilder.fromHttpUrl(config.getBaseUrl())
                .path("/forecast")
                .queryParam("q", city)
                .queryParam("appid", config.getApiKey())
                .queryParam("units", "metric")
                .build()
                .toUriString();

        return restClient.get()
                .uri(url)
                .retrieve()
                .body(Map.class);
    }

    private Map<String, Object> demoCurrentWeather(String city) {
        long now = System.currentTimeMillis() / 1000;
        return Map.of(
                "name", city,
                "main", Map.of(
                        "temp", 31.5,
                        "feels_like", 35.2,
                        "humidity", 72,
                        "pressure", 1008
                ),
                "visibility", 9000,
                "wind", Map.of("speed", 4.2),
                "rain", Map.of("1h", 0.8),
                "weather", List.of(Map.of("main", "Clouds", "icon", "03d")),
                "sys", Map.of("country", "IN", "sunrise", now - 14400, "sunset", now + 14400)
        );
    }

    private Map<String, Object> demoForecast() {
        long now = System.currentTimeMillis() / 1000;
        return Map.<String, Object>of("list", java.util.stream.IntStream.range(1, 41)
                .mapToObj(index -> Map.of(
                        "dt", now + (index * 10800L),
                        "main", Map.of(
                                "temp", 28 + (index % 6),
                                "temp_min", 26 + (index % 4),
                                "temp_max", 31 + (index % 5),
                                "humidity", 60 + (index % 25)
                        ),
                        "wind", Map.of("speed", 2.5 + (index % 5)),
                        "weather", List.of(Map.of(
                                "main", index % 4 == 0 ? "Rain" : "Clouds",
                                "icon", index % 4 == 0 ? "10d" : "03d"
                        ))
                ))
                .toList());
    }
}
