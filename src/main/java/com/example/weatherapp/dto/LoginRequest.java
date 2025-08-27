package com.example.weatherapp.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * ログイン用リクエストDTO
 */
@Getter
@Setter
public class LoginRequest {
    private String username;
    private String password;
}
