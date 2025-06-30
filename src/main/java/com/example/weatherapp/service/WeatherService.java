package com.example.weatherapp.service;

import org.springframework.stereotype.Service;

/**
 * 都道府県の名前をもとに、天気情報を取得するサービスクラス。
 */
@Service
public class WeatherService {

    /**
     * 指定された都道府県の天気情報を取得します。
     *
     * @param prefecture 都道府県名（例："東京"）
     * @return 天気の説明（例："晴れ", "曇り", "雨"など）
     */
    public String getWeatherForPrefecture(String prefecture) {
        return "晴れ"; // 仮の実装
    }
}
