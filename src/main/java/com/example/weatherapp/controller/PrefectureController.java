package com.example.weatherapp.controller;

import com.example.weatherapp.api.ApiResponse;
import com.example.weatherapp.api.PrefectureRequest;
import com.example.weatherapp.api.PrefectureResponse;
import com.example.weatherapp.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 都道府県情報の登録・更新を担当するRESTコントローラー。
 */
@RestController
@RequestMapping("/prefecture")
public class PrefectureController {

    private final WeatherService weatherService;

    public PrefectureController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * 新規にユーザーの選択した都道府県を保存するPOSTエンドポイント。
     *
     * @param request 都道府県保存用リクエストデータ
     * @return 保存した都道府県情報を含むAPIレスポンス
     */
    @PostMapping
    public ResponseEntity<ApiResponse<PrefectureResponse>> savePrefecture(@RequestBody PrefectureRequest request) {
        PrefectureResponse response = weatherService.savePrefecture(request);
        if (response == null) {
            return ResponseEntity.status(400).body(new ApiResponse<>("400 Bad Request", null));
        }
        return ResponseEntity.ok(new ApiResponse<>("200 OK", response));
    }

    /**
     * 既存の都道府県情報を更新するPUTエンドポイント。
     *
     * @param request 都道府県更新用リクエストデータ
     * @return 更新した都道府県情報を含むAPIレスポンス
     */
    @PutMapping
    public ResponseEntity<ApiResponse<PrefectureResponse>> updatePrefecture(@RequestBody PrefectureRequest request) {
        PrefectureResponse response = weatherService.updatePrefecture(request);
        if (response == null) {
            return ResponseEntity.status(400).body(new ApiResponse<>("400 Bad Request", null));
        }
        return ResponseEntity.ok(new ApiResponse<>("200 OK", response));
    }
}
