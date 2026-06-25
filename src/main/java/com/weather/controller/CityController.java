package com.weather.controller;

import com.weather.dto.city.CityResponseDto;
import com.weather.service.CityService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("/search")
    public List<CityResponseDto> search(
            @RequestParam String keyword) {

        return cityService.searchCities(keyword);
    }
}