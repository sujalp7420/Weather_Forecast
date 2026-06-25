package com.weather.controller;

import com.weather.dto.auth.RegisterRequestDto;
import com.weather.exception.UserAlreadyExistsException;
import com.weather.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequestDto());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(
            @Valid
            @ModelAttribute("registerRequest") RegisterRequestDto request,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "auth/register";
        }

        try {
            authService.register(request);
        } catch (UserAlreadyExistsException ex) {
            model.addAttribute("registrationError", ex.getMessage());
            return "auth/register";
        }

        return "redirect:/login?registered";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "auth/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(
            @RequestParam String email,
            Model model) {
        return "auth/forgot-password";
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(
            @RequestParam String token,
            Model model) {
        model.addAttribute("token", token);
        return "auth/reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestParam String token,
            @RequestParam String password) {
        return "redirect:/login?reset";
    }
}
