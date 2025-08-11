package com.example.weatherapp.controller;

import com.example.weatherapp.api.ApiResponse;
import com.example.weatherapp.api.PrefectureRequest;
import com.example.weatherapp.api.PrefectureResponse;
import com.example.weatherapp.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 天気情報関連のREST APIを提供するコントローラークラス。
 * <p>
 * ユーザーのダッシュボード表示用の天気情報取得、
 * 都道府県の新規保存および更新を担当します。
 * </p>
 */
@RestController
@RequestMapping("/weather")
public class WeatherController {

    /**
     * 天気情報のビジネスロジックを担当するサービス。
     */
    private final WeatherService weatherService;

    /**
     * コンストラクタによるWeatherServiceの注入。
     *
     * @param weatherService 天気サービスの実装
     */
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * ユーザーのダッシュボード向け天気情報を取得するGETエンドポイント。
     * <p>
     * ユーザーIDを指定して、そのユーザーの登録した都道府県の天気情報を返します。
     * データが存在しなければ404 Not Foundを返します。
     * </p>
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

    /**
     * 新規にユーザーの選択した都道府県を保存するPOSTエンドポイント。
     * <p>
     * リクエストボディの都道府県情報を元に保存処理を行い、
     * 成功時には保存結果の都道府県情報を返します。
     * 入力エラー等で保存に失敗した場合は400 Bad Requestを返します。
     * </p>
     *
     * @param request 都道府県保存用リクエストデータ
     * @return 保存した都道府県情報を含むAPIレスポンス
     */
    @PostMapping("/prefecture")
    public ResponseEntity<ApiResponse<PrefectureResponse>> savePrefecture(@RequestBody PrefectureRequest request) {
        PrefectureResponse response = weatherService.savePrefecture(request);
        if (response == null) {
            return ResponseEntity.status(400).body(new ApiResponse<>("400 Bad Request", null));
        }
        return ResponseEntity.ok(new ApiResponse<>("200 OK", response));
    }

    /**
     * 既存の都道府県情報を更新するPUTエンドポイント。
     * <p>
     * リクエストボディの内容に基づいて都道府県情報を更新し、
     * 更新結果の情報を返します。
     * 入力エラー等で更新に失敗した場合は400 Bad Requestを返します。
     * </p>
     *
     * @param request 都道府県更新用リクエストデータ
     * @return 更新した都道府県情報を含むAPIレスポンス
     */
    @PutMapping("/prefecture")
    public ResponseEntity<ApiResponse<PrefectureResponse>> updatePrefecture(@RequestBody PrefectureRequest request) {
        PrefectureResponse response = weatherService.updatePrefecture(request);
        if (response == null) {
            return ResponseEntity.status(400).body(new ApiResponse<>("400 Bad Request", null));
        }
        return ResponseEntity.ok(new ApiResponse<>("200 OK", response));
    }
}
