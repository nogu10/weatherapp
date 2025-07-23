package com.example.weatherapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * ユーザーを表すエンティティクラス。
 * ユーザー名、パスワード、選択した都道府県および登録日時を保持します。
 */
@Entity
@Getter
@Setter
public class User {

    /**
     * ユーザーID（主キー、自動生成）
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
     * 選択した都道府県。
     * {@link PrefectureConverter} によりDBの整数コードと変換されます。
     */
    @Convert(converter = PrefectureConverter.class)
    private Prefecture selectedPrefecture;

    /**
     * ユーザー登録日時。
     * レコード作成時に自動で設定されます。
     */
    private LocalDateTime registerAt;

    /**
     * 永続化前の処理。
     * 登録日時が未設定の場合に現在日時を設定します。
     */
    @PrePersist
    public void prePersist() {
        if (registerAt == null) {
            registerAt = LocalDateTime.now();
        }
    }
}
