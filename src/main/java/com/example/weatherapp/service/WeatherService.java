package com.example.weatherapp.service;

import com.example.weatherapp.api.PrefectureRequest;
import com.example.weatherapp.api.PrefectureResponse;

public interface WeatherService {

    /** ダッシュボード表示用にユーザーIDから都道府県と天気情報を取得 */
    PrefectureResponse getDashboard(Long userId);

    /** 新規に都道府県を保存 */
    PrefectureResponse savePrefecture(PrefectureRequest request);

    /** 既存の都道府県を更新 */
    PrefectureResponse updatePrefecture(PrefectureRequest request);
}
