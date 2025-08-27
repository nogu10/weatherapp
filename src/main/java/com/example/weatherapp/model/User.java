package com.example.weatherapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * ユーザー情報エンティティ
 */
@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private Integer selectedPrefecture;

    private LocalDateTime registerAt;

    @PrePersist
    public void prePersist() {
        if (registerAt == null) {
            registerAt = LocalDateTime.now();
        }
    }
}
