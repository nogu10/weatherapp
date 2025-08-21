package com.example.weatherapp.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * ユーザー登録用リクエストDTO
 */
@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;
}
