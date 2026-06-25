package com.weather.config;

import com.weather.entity.Role;
import com.weather.entity.User;
import com.weather.repository.RoleRepository;
import com.weather.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner seedSecurityData() {
        return args -> {
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_USER").build()));

            String demoEmail = System.getenv().getOrDefault("DEMO_EMAIL", "demo@weather.local");
            String demoPassword = System.getenv().getOrDefault("DEMO_PASSWORD", "Demo@12345");

            if (!userRepository.existsByEmail(demoEmail)) {
                userRepository.save(User.builder()
                        .fullName("Demo User")
                        .email(demoEmail)
                        .password(passwordEncoder.encode(demoPassword))
                        .enabled(true)
                        .roles(Set.of(userRole))
                        .build());
            }
        };
    }
}
