package com.example.weatherapp.api;

public class PrefectureUpdateRequest {
    private Long userId;
    private int prefectureCode;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getPrefectureCode() {
        return prefectureCode;
    }

    public void setPrefectureCode(int prefectureCode) {
        this.prefectureCode = prefectureCode;
    }
}
