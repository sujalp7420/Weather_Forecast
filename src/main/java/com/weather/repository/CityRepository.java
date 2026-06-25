package com.weather.repository;

import com.weather.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findByCityName(String cityName);

    Optional<City> findByCityNameIgnoreCase(String cityName);

    List<City> findByCityNameContainingIgnoreCase(String keyword);
}
