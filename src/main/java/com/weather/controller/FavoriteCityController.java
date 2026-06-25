package com.weather.controller;

import com.weather.service.FavoriteCityService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteCityController {

    private final FavoriteCityService favoriteCityService;

    @GetMapping
    public String favorites(
            @RequestParam Long userId,
            Model model) {

        model.addAttribute(
                "favorites",
                favoriteCityService.getFavorites(userId));

        return "favorite/favorites";
    }
}