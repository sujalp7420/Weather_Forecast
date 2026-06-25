package com.weather.service.impl;

import com.weather.client.OpenWeatherClient;
import com.weather.dto.weather.AnalyticsPointDto;
import com.weather.dto.weather.ForecastDto;
import com.weather.dto.weather.WeatherResponseDto;
import com.weather.entity.City;
import com.weather.entity.SearchHistory;
import com.weather.entity.WeatherData;
import com.weather.repository.CityRepository;
import com.weather.repository.SearchHistoryRepository;
import com.weather.repository.UserRepository;
import com.weather.repository.WeatherDataRepository;
import com.weather.service.WeatherService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl
        implements WeatherService {

    private final OpenWeatherClient client;
    private final CityRepository cityRepository;
    private final UserRepository userRepository;
    private final SearchHistoryRepository searchHistoryRepository;
    private final WeatherDataRepository weatherDataRepository;

    @Override
    @Transactional
    public WeatherResponseDto getCurrentWeather(
            String city) {

        Map<String,Object> response =
                client.getCurrentWeather(city);

        Map<String,Object> main =
                (Map<String,Object>)
                        response.get("main");

        Map<String,Object> wind =
                (Map<String,Object>)
                        response.get("wind");

        List<Map<String,Object>> weather =
                (List<Map<String,Object>>)
                        response.get("weather");

        Map<String,Object> sys = (Map<String,Object>) response.get("sys");
        Map<String,Object> rain = (Map<String,Object>) response.getOrDefault("rain", Map.of());

        String resolvedCity = Objects.toString(response.getOrDefault("name", city));
        String country = sys == null ? "NA" : Objects.toString(sys.getOrDefault("country", "NA"));
        City savedCity = cityRepository.findByCityNameIgnoreCase(resolvedCity)
                .orElseGet(() -> cityRepository.save(City.builder()
                        .cityName(resolvedCity)
                        .country(country)
                        .build()));

        Double temperature = number(main, "temp").doubleValue();
        Integer humidity = number(main, "humidity").intValue();
        String condition = weather.get(0).get("main").toString();
        String icon = weather.get(0).get("icon").toString();

        weatherDataRepository.save(WeatherData.builder()
                .city(savedCity)
                .temperature(temperature)
                .feelsLike(number(main, "feels_like").doubleValue())
                .humidity(humidity)
                .pressure(number(main, "pressure").doubleValue())
                .visibilityMeters(number(response, "visibility").intValue())
                .windSpeed(number(wind, "speed").doubleValue())
                .rainfallMm(number(rain, "1h").doubleValue())
                .weatherCondition(condition)
                .icon(icon)
                .sunriseAt(epoch(sys, "sunrise"))
                .sunsetAt(epoch(sys, "sunset"))
                .build());

        saveSearchHistory(resolvedCity);

        return WeatherResponseDto.builder()
                .city(resolvedCity)
                .temperature(temperature)
                .feelsLike(number(main, "feels_like").doubleValue())
                .humidity(humidity)
                .pressure(number(main, "pressure").doubleValue())
                .visibilityMeters(number(response, "visibility").intValue())
                .windSpeed(number(wind, "speed").doubleValue())
                .condition(condition)
                .icon(icon)
                .sunrise(format(epoch(sys, "sunrise")))
                .sunset(format(epoch(sys, "sunset")))
                .build();
    }

    @Override
    public List<ForecastDto> getHourlyForecast(String city) {
        return forecastItems(city).stream()
                .limit(8)
                .map(this::toForecastDto)
                .toList();
    }

    @Override
    public List<ForecastDto> getDailyForecast(String city) {
        return forecastItems(city).stream()
                .map(this::toForecastDto)
                .collect(Collectors.groupingBy(ForecastDto::getDate))
                .entrySet()
                .stream()
                .limit(7)
                .map(entry -> {
                    List<ForecastDto> points = entry.getValue();
                    return ForecastDto.builder()
                            .date(entry.getKey())
                            .minTemp(points.stream().map(ForecastDto::getMinTemp).min(Double::compareTo).orElse(0.0))
                            .maxTemp(points.stream().map(ForecastDto::getMaxTemp).max(Double::compareTo).orElse(0.0))
                            .humidity((int) points.stream().mapToInt(ForecastDto::getHumidity).average().orElse(0))
                            .windSpeed(points.stream().mapToDouble(ForecastDto::getWindSpeed).average().orElse(0))
                            .condition(points.get(0).getCondition())
                            .icon(points.get(0).getIcon())
                            .build();
                })
                .toList();
    }

    @Override
    public List<AnalyticsPointDto> getAnalytics(String city) {
        return cityRepository.findByCityNameIgnoreCase(city)
                .map(weatherDataRepository::findTop24ByCityOrderByRecordedAtDesc)
                .orElse(List.of())
                .stream()
                .sorted(Comparator.comparing(WeatherData::getRecordedAt))
                .map(data -> AnalyticsPointDto.builder()
                        .label(data.getRecordedAt().format(DateTimeFormatter.ofPattern("HH:mm")))
                        .temperature(data.getTemperature())
                        .humidity(data.getHumidity())
                        .rainfall(data.getRainfallMm())
                        .windSpeed(data.getWindSpeed())
                        .build())
                .toList();
    }

    private List<Map<String, Object>> forecastItems(String city) {
        Map<String, Object> forecast = client.getForecast(city);
        return (List<Map<String, Object>>) forecast.getOrDefault("list", List.of());
    }

    private ForecastDto toForecastDto(Map<String, Object> item) {
        Map<String, Object> main = (Map<String, Object>) item.get("main");
        Map<String, Object> wind = (Map<String, Object>) item.get("wind");
        List<Map<String, Object>> weather = (List<Map<String, Object>>) item.get("weather");
        LocalDateTime dateTime = epoch(item, "dt");

        return ForecastDto.builder()
                .date(dateTime.toLocalDate().toString())
                .time(dateTime.format(DateTimeFormatter.ofPattern("HH:mm")))
                .temperature(number(main, "temp").doubleValue())
                .minTemp(number(main, "temp_min").doubleValue())
                .maxTemp(number(main, "temp_max").doubleValue())
                .humidity(number(main, "humidity").intValue())
                .windSpeed(number(wind, "speed").doubleValue())
                .condition(weather.get(0).get("main").toString())
                .icon(weather.get(0).get("icon").toString())
                .build();
    }

    private void saveSearchHistory(String city) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return;
        }
        userRepository.findByEmail(authentication.getName())
                .ifPresent(user -> searchHistoryRepository.save(SearchHistory.builder()
                        .user(user)
                        .cityName(city)
                        .build()));
    }

    private Number number(Map<String, Object> source, String key) {
        Object value = source == null ? null : source.get(key);
        return value instanceof Number number ? number : 0;
    }

    private LocalDateTime epoch(Map<String, Object> source, String key) {
        return LocalDateTime.ofInstant(
                Instant.ofEpochSecond(number(source, key).longValue()),
                ZoneId.systemDefault());
    }

    private String format(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
