package com.example.weatherapp.model;

import jakarta.persistence.*;

/**
 * ユーザーが最後に選択した都道府県の情報を保存するエンティティ。
 */
@Entity
public class PrefectureSelection {

    /**
     * ID（主キー、自動生成）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ユーザーが選んだ都道府県名（例：東京、大阪）
     */
    private String prefecture;

    /**
     * この選択が紐づいているユーザー
     */
    @ManyToOne
    private User user;
}
