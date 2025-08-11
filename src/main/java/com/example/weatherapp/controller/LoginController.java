package com.example.weatherapp.controller;

import com.example.weatherapp.api.ApiResponse;
import com.example.weatherapp.api.LoginRequest;
import com.example.weatherapp.api.UserResponse;
import com.example.weatherapp.model.User;
import com.example.weatherapp.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST APIでのログイン認証を提供するコントローラークラス。
 * <p>
 * クライアントからのログインリクエストを受け取り、
 * 認証成功時はセッションにユーザー情報を保存し、
 * ユーザー情報を含むレスポンスを返します。
 * 認証失敗やリクエスト不備の場合は適切なHTTPステータスを返します。
 * </p>
 */
@RestController
@RequestMapping("/login")
public class LoginApiController {

    /**
     * ユーザー認証を行うサービスクラスのインスタンス。
     */
    private final UserService userService;

    /**
     * コンストラクタによるUserServiceの注入。
     *
     * @param userService ユーザーサービス
     */
    public LoginApiController(UserService userService) {
        this.userService = userService;
    }

    /**
     * ログイン処理用POSTエンドポイント。
     * <p>
     * リクエストボディのユーザー名・パスワードをチェックし、
     * 認証に成功すればセッションにユーザーIDとユーザー名を保存して
     * ユーザー情報をレスポンスとして返します。
     * </p>
     *
     * @param request ログイン情報を保持するリクエストボディ
     * @param session HTTPセッション（ユーザー情報保存用）
     * @return 認証結果を含むAPIレスポンス。認証失敗時は401 Unauthorized、入力不備は400 Bad Requestを返す。
     */
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> login(@RequestBody LoginRequest request, HttpSession session) {
        // 入力チェック：ユーザー名またはパスワードがnullなら400エラー
        if (request.getUsername() == null || request.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>("400 Bad Request", null));
        }

        // サービスを使って認証を実施
        User user = userService.login(request.getUsername(), request.getPassword());

        // 認証失敗なら401 Unauthorizedを返す
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>("401 Unauthorized", null));
        }

        // 認証成功時はセッションにユーザー情報を保存
        session.setAttribute("userId", user.getId());
        session.setAttribute("username", user.getUsername());

        // ユーザーエンティティからレスポンスDTOに変換
        UserResponse response = UserResponse.fromEntity(user);

        // 200 OKとともにユーザー情報を返す
        return ResponseEntity.ok(new ApiResponse<>("200 OK", response));
    }
}
