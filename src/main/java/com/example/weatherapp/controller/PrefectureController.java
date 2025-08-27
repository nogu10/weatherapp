package com.example.weatherapp.controller;

import com.example.weatherapp.api.ApiResponse;
import com.example.weatherapp.api.PrefectureRequest;
import com.example.weatherapp.api.PrefectureResponse;
import com.example.weatherapp.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ユーザーの都道府県設定・変更を担当するコントローラー。
 * 
 * POST /weather    : 初回都道府県設定
 * PUT  /prefecture : 都道府県変更
 */
@RestController
public class PrefectureController {

    private final WeatherService weatherService;

    public PrefectureController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * ユーザーが初めて都道府県を選択して保存するエンドポイント。
     * @param request ユーザーIDと都道府県コード
     * @return 保存結果を返すAPIレスポンス
     */
    @PostMapping("/prefecture")
    public ResponseEntity<ApiResponse<PrefectureResponse>> savePrefecture(@RequestBody PrefectureRequest request) {
        PrefectureResponse response = weatherService.savePrefecture(request);
        if (response == null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("400 Bad Request", null));
        }
        return ResponseEntity.ok(new ApiResponse<>("200 OK", response));
    }

    /**
     * ユーザーが都道府県を変更して保存するエンドポイント。
     * @param request ユーザーIDと変更後の都道府県コード
     * @return 更新結果を返すAPIレスポンス
     */
    @PutMapping("/prefecture")
    public ResponseEntity<ApiResponse<PrefectureResponse>> updatePrefecture(@RequestBody PrefectureRequest request) {
        PrefectureResponse response = weatherService.updatePrefecture(request);
        if (response == null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("400 Bad Request", null));
        }
        return ResponseEntity.ok(new ApiResponse<>("200 OK", response));
    }
}
