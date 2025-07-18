package com.example.weatherapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * ユーザーを表すエンティティクラス。
 * ユーザー名・パスワードおよび選択した都道府県を保持します。
 */
@Entity
@Getter
@Setter
public class User {

    /**
     * ユーザーID（主キー）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ユーザー名（一意制約あり）
     */
    @Column(unique = true)
    private String username;

    /**
     * パスワード（平文保存は推奨されません）
     */
    private String password;

    /**
     * 選択した都道府県
     * {@link PrefectureConverter} によってDBの整数コードと変換されます。
     */
    @Convert(converter = PrefectureConverter.class)
    private Prefecture selectedPrefecture;
}

