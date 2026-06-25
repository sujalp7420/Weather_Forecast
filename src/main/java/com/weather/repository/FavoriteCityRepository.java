package com.weather.repository;

import com.weather.entity.FavoriteCity;
import com.weather.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteCityRepository extends JpaRepository<FavoriteCity, Long> {

    List<FavoriteCity> findByUser(User user);
}