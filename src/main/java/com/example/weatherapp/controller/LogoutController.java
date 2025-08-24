package com.example.weatherapp.controller;

import com.example.weatherapp.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ログアウト用APIコントローラー
 * 
 * フロントエンド側で認証情報（セッション、JWT、ローカルストレージ等）を破棄させる。
 * バックエンド側はログアウト完了のレスポンスを返すのみ。
 */
@RestController
@RequestMapping("/api/logout")
public class LogoutController {

    /**
     * ログアウト処理
     *
     * @return ログアウト成功レスポンス
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Object>> logout() {
        return ResponseEntity.ok(ApiResponse.success("ログアウトしました"));
    }
}
