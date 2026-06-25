package com.weather.repository;

import com.weather.entity.WeatherAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherAlertRepository extends JpaRepository<WeatherAlert, Long> {
}