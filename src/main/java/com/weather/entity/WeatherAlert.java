package com.weather.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "weather_alerts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String alertType;

    @Column(length = 1000)
    private String description;

    @Column(length = 120)
    private String cityName;

    private String severity;

    private boolean active;

    private LocalDateTime issuedAt;

    @PrePersist
    void onCreate() {
        issuedAt = LocalDateTime.now();
        active = true;
    }
}
