package com.weather.repository;

import com.weather.entity.SearchHistory;
import com.weather.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    List<SearchHistory> findByUserOrderBySearchedAtDesc(User user);
}