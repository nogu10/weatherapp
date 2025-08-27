package com.example.weatherapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * API共通レスポンス用DTO
 * @param <T> レスポンスデータの型
 */
@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private String status;
    private T data;
}
