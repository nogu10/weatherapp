package com.example.weatherapp.model;

import jakarta.persistence.*;

/**
 * アプリケーションのユーザー情報を表すエンティティクラス。
 * ユーザー名とパスワードを保持します。
 */
@Entity
public class User {

    /**
     * ユーザーID（主キー、自動生成）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ログイン用のユーザー名
     */
    private String username;

    /**
     * ログイン用のパスワード（ハッシュ化推奨）
     */
    private String password;
}
