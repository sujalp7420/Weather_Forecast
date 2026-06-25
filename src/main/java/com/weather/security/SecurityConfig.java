package com.weather.security;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/",
                                "/login",
                                "/register",
                                "/forgot-password",
                                "/reset-password",
                                "/css/**",
                                "/js/**",
                                "/images/**")
                        .permitAll()

                        .requestMatchers("/user/**")
                        .hasRole("USER")

                        .anyRequest()
                        .authenticated()
                )

                .formLogin(form -> form

                        .loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")

                        .defaultSuccessUrl(
                                "/dashboard",
                                true)

                        .permitAll()
                )

                .logout(logout -> logout

                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))

                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")

                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionFixation(sessionFixation -> sessionFixation.migrateSession())
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false));

        return http.build();
    }
}
