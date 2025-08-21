package com.example.weatherapp.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 都道府県選択・更新リクエストDTO
 */
@Getter
@Setter
public class PrefectureRequest {
    private Long userId;
    private int prefectureCode;
}
