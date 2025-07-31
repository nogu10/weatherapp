package com.example.weatherapp.api;

import com.example.weatherapp.model.Prefecture;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 都道府県の天気情報をAPIレスポンス用に表現するDTOクラス。
 * <p>
 * 都道府県名（Enum定数名）、気温、日付、天気状態を保持します。
 * </p>
 */
@Getter
@AllArgsConstructor
public class PrefectureResponse {

    /**
     * 都道府県のEnum定数名。
     * 例: "TOKYO", "OSAKA"
     */
    private String name;

    /**
     * 気温（摂氏）。
     */
    private double temperature;

    /**
     * 対象日付（YYYY-MM-DD形式の文字列）。
     */
    private String date;

    /**
     * 天気状態を示すフラグ。
     * <ul>
     *   <li>true: 晴れなど良い天気</li>
     *   <li>false: 雨や曇りなど悪天候</li>
     * </ul>
     */
    private boolean weather;

    /**
     * 指定された都道府県と天気情報からPrefectureResponseを生成するファクトリメソッド。
     *
     * @param prefecture 都道府県のEnum値
     * @param temperature 気温（摂氏）
     * @param date 日付（YYYY-MM-DD形式）
     * @param weather 天気状態（晴れ=true、雨や曇り=false）
     * @return PrefectureResponseのインスタンス
     */
    public static PrefectureResponse from(Prefecture prefecture, double temperature, String date, boolean weather) {
        return new PrefectureResponse(prefecture.name(), temperature, date, weather);
    }
}
