package com.example.weatherapp.api;

/**
 * リクエストボディとして使用される、都道府県の更新リクエストクラス。
 * <p>
 * ユーザーIDと選択された都道府県コードを保持します。
 * このクラスは、JSONリクエストのマッピングに使用されます。
 * </p>
 */
public class PrefectureUpdateRequest {

    /**
     * ユーザーID（ログインユーザーの識別子）
     */
    private Long userId;

    /**
     * 都道府県コード（例：13 = 東京都）
     */
    private int prefectureCode;

    /**
     * ユーザーIDを取得します。
     *
     * @return ユーザーID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * ユーザーIDを設定します。
     *
     * @param userId ユーザーID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 都道府県コードを取得します。
     *
     * @return 都道府県コード
     */
    public int getPrefectureCode() {
        return prefectureCode;
    }

    /**
     * 都道府県コードを設定します。
     *
     * @param prefectureCode 都道府県コード
     */
    public void setPrefectureCode(int prefectureCode) {
        this.prefectureCode = prefectureCode;
    }
}
