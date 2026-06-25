package com.weather.util;

public final class WeatherThemeResolver {

    private WeatherThemeResolver() {
    }

    public static String resolve(String condition, String icon) {
        String normalized = condition == null ? "" : condition.toLowerCase();
        String theme;
        if (normalized.contains("thunder")) {
            theme = "thunderstorm";
        } else if (normalized.contains("drizzle")) {
            theme = "drizzle";
        } else if (normalized.contains("rain")) {
            theme = "rain";
        } else if (normalized.contains("snow")) {
            theme = "snow";
        } else if (normalized.contains("mist") || normalized.contains("fog") || normalized.contains("haze")) {
            theme = "mist";
        } else if (normalized.contains("clear")) {
            theme = "clear";
        } else if (normalized.contains("cloud")) {
            theme = "clouds";
        } else {
            theme = "default";
        }
        boolean night = icon != null && icon.endsWith("n");
        return "weather-" + theme + (night ? " night" : "");
    }
}
