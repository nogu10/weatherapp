package com.example.weatherapp.api;

import lombok.Getter;
import lombok.Setter;

/**
 * ユーザーの選択した都道府県を更新するためのリクエストデータを表すクラス。
 * <p>
 * クライアントから送信されるユーザーIDと都道府県コードを保持します。
 * </p>
 */
@Getter
@Setter
public class PrefectureUpdateRequest {

    /**
     * 更新対象のユーザーID
     */
    private Long userId;

    /**
     * 新しく選択された都道府県コード
     */
    private int prefectureCode;
}
