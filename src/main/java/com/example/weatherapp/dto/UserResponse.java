package com.example.weatherapp.api;

import com.example.weatherapp.model.Prefecture;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

/**
 * ユーザー情報のレスポンス用DTOクラス。
 * <p>
 * ユーザーエンティティから必要な情報を抽出し、
 * APIレスポンス用に整形したデータを保持します。
 * </p>
 */
@Getter
@AllArgsConstructor
public class UserResponse {

    /**
     * ユーザーID
     */
    private Long id;

    /**
     * ユーザー名
     */
    private String username;

    /**
     * ユーザー登録日時のISO 8601形式文字列
     */
    private String registerAt;

    /**
     * UserエンティティからUserResponseオブジェクトを生成するファクトリメソッド。
     * <p>
     * 登録日時はISO 8601形式（例：2023-07-23T15:30:00）に変換されます。
     * </p>
     *
     * @param user ユーザーエンティティ
     * @return UserResponseインスタンス
     */
    public static UserResponse fromEntity(com.example.weatherapp.model.User user) {
        String formattedDate = user.getRegisterAt() != null ?
                user.getRegisterAt().format(DateTimeFormatter.ISO_DATE_TIME) : null;
        return new UserResponse(user.getId(), user.getUsername(), formattedDate);
    }
}
