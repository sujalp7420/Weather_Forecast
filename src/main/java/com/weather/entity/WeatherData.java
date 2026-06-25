package com.weather.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "weather_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    private Double temperature;

    private Double feelsLike;

    private Integer humidity;

    private Double pressure;

    private Double windSpeed;

    private Double uvIndex;

    private Integer visibilityMeters;

    private LocalDateTime sunriseAt;

    private LocalDateTime sunsetAt;

    private Double rainfallMm;

    @Column(length = 120)
    private String weatherCondition;

    @Column(length = 20)
    private String icon;

    private LocalDateTime recordedAt;

    @PrePersist
    void onCreate() {
        recordedAt = LocalDateTime.now();
    }
}
