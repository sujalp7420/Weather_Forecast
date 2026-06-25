package com.weather.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "favorite_cities",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_favorite_user_city",
                columnNames = {"user_id", "city_id"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteCity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;
}
