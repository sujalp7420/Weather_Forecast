package com.weather.controller;

import com.weather.dto.weather.WeatherResponseDto;
import com.weather.service.WeatherService;
import com.weather.util.WeatherThemeResolver;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping
    public String weatherPage(Model model) {
        model.addAttribute("weatherTheme", "weather-default");
        return "weather/weather";
    }

    @PostMapping("/search")
    public String searchWeather(
            @RequestParam String city,
            Model model) {

        WeatherResponseDto weather = weatherService.getCurrentWeather(city);

        model.addAttribute("weather", weather);
        model.addAttribute("weatherTheme", WeatherThemeResolver.resolve(weather.getCondition(), weather.getIcon()));

        return "weather/weather";
    }
}
