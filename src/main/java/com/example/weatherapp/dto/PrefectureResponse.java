package com.example.weatherapp.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 都道府県とその天気情報を返すレスポンス用DTO
 */
@Getter
@AllArgsConstructor
public class PrefectureResponse {

    /** ユーザーID */
    private Long userId;

    /** 都道府県コード（Enum定数名） */
    private String code;

    /** 気温（℃） */
    private Double temperature;

    /** 天気（晴れ:true, 雨:falseなど） */
    private String weather;

    /** 天気情報の日付（YYYY-MM-DD） */
    private String date;
}
