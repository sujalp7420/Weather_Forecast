package com.weather.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "cities",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_city_country",
                columnNames = {"city_name", "country"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city_name", nullable = false, length = 120)
    private String cityName;

    @Column(nullable = false, length = 80)
    private String country;

    private Double latitude;

    private Double longitude;
}
