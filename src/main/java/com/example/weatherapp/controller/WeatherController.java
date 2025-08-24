package com.example.weatherapp.controller;

import com.example.weatherapp.dto.ApiResponse;
import com.example.weatherapp.dto.PrefectureResponse;
import com.example.weatherapp.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 天気情報を取得するコントローラ
 */
@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    /**
     * ユーザーの選択した都道府県に基づいて
     * 現在の天気情報を取得する
     *
     * @param userId ユーザーID
     * @return 天気情報を含むレスポンス
     */
    @GetMapping("/dashboard/{userId}")
    public ApiResponse<PrefectureResponse> getDashboard(@PathVariable Long userId) {
        PrefectureResponse weather = weatherService.getDashboard(userId);

        if (weather == null) {
            return new ApiResponse<>("error", "天気情報を取得できませんでした", null);
        }

        return new ApiResponse<>("success", "天気情報を取得しました", weather);
    }
}
