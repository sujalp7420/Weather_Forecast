package com.weather.controller;

import com.weather.service.WeatherService;
import com.weather.util.WeatherThemeResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final WeatherService weatherService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(
            @RequestParam(defaultValue = "London") String city,
            Model model) {
        var weather = weatherService.getCurrentWeather(city);
        model.addAttribute("weather", weather);
        model.addAttribute("hourlyForecast", weatherService.getHourlyForecast(city));
        model.addAttribute("dailyForecast", weatherService.getDailyForecast(city));
        model.addAttribute("analytics", weatherService.getAnalytics(city));
        model.addAttribute("weatherTheme", WeatherThemeResolver.resolve(weather.getCondition(), weather.getIcon()));
        return "dashboard";
    }
}
