package com.example.weatherapp.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * APIレスポンスを表す汎用クラス。
 * <p>
 * クライアントへ返すレスポンスの共通フォーマットとして、
 * ステータス文字列と任意のデータ本体を保持します。
 * </p>
 *
 * @param <T> レスポンスデータの型
 */
@Getter
@Setter
@AllArgsConstructor
public class ApiResponse<T> {

    /**
     * レスポンスのステータスを示す文字列。
     * 例: "200 OK"、"400 Bad Request" など
     */
    private String status;

    /**
     * 実際のレスポンスデータ本体。
     */
    private T data;
}
