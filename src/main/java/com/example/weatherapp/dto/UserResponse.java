package com.example.weatherapp.dto;

import com.example.weatherapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

/**
 * ユーザー情報レスポンスDTO
 */
@Getter
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String registerAt;

    public static UserResponse fromEntity(User user) {
        String formattedDate = user.getRegisterAt() != null
                ? user.getRegisterAt().format(DateTimeFormatter.ISO_DATE_TIME)
                : null;
        return new UserResponse(user.getId(), user.getUsername(), formattedDate);
    }
}
