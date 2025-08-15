package com.example.weatherapp.controller;

import com.example.weatherapp.api.ApiResponse;
import com.example.weatherapp.api.PrefectureResponse;
import com.example.weatherapp.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 天気情報関連のREST APIを提供するコントローラークラス。
 * ユーザーのダッシュボード表示用の天気情報取得を担当します。
 */
@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * ユーザーのダッシュボード向け天気情報を取得するGETエンドポイント。
     *
     * @param userId ユーザーID（パスパラメータ）
     * @return 天気情報を含むAPIレスポンス
     */
    @GetMapping("/dashboard/{userId}")
    public ResponseEntity<ApiResponse<PrefectureResponse>> getDashboard(@PathVariable Long userId) {
        PrefectureResponse prefectureData = weatherService.getDashboard(userId);
        if (prefectureData == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>("404 Not Found", null));
        }
        return ResponseEntity.ok(new ApiResponse<>("200 OK", prefectureData));
    }
}
