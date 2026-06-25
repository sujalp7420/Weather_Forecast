package com.weather.repository;

import com.weather.entity.City;
import com.weather.entity.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    List<WeatherData> findByCity(City city);

    List<WeatherData> findTop24ByCityOrderByRecordedAtDesc(City city);

    List<WeatherData> findTop7ByCityOrderByRecordedAtDesc(City city);
}