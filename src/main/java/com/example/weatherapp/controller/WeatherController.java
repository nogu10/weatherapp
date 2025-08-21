package com.example.weatherapp.controller;

import com.example.weatherapp.api.ApiResponse;
import com.example.weatherapp.api.PrefectureResponse;
import com.example.weatherapp.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ダッシュボード表示用の天気情報取得を担当するコントローラー。
 * GET /dashboard?userId={userId} で指定ユーザーの天気情報を取得
 */
@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * 指定ユーザーの登録都道府県の天気情報を取得するエンドポイント。
     * @param userId ユーザーID（クエリパラメータ）
     * @return 天気情報を含むAPIレスポンス
     */
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<PrefectureResponse>> getDashboard(@RequestParam Long userId) {
        PrefectureResponse prefectureData = weatherService.getDashboard(userId);
        if (prefectureData == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>("404 Not Found", null));
        }
        return ResponseEntity.ok(new ApiResponse<>("200 OK", prefectureData));
    }
}
