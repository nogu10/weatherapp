package com.example.weatherapp.api;

import lombok.Getter;
import lombok.Setter;

/**
 * ログイン処理用のリクエストデータを表すクラス。
 * <p>
 * クライアントから送信されるユーザー名とパスワードを保持します。
 * </p>
 */
@Getter
@Setter
public class LoginRequest {

    /**
     * ユーザー名
     */
    private String username;

    /**
     * パスワード
     */
    private String password;
}
