package com.example.weatherapp.api;

import lombok.Getter;
import lombok.Setter;

/**
 * ユーザー登録用のリクエストデータを表すクラス。
 * <p>
 * クライアントから送信される新規登録ユーザーのユーザー名とパスワードを保持します。
 * </p>
 */
@Getter
@Setter
public class RegisterRequest {

    /**
     * 登録するユーザー名
     */
    private String username;

    /**
     * 登録するパスワード
     */
    private String password;
}
