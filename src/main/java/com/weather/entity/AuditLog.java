package com.weather.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String action;

    @Column(length = 160)
    private String username;

    private LocalDateTime actionTime;

    @PrePersist
    void onCreate() {
        actionTime = LocalDateTime.now();
    }
}
