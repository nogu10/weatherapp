package com.example.weatherapp.controller;

import com.example.weatherapp.api.*;
import com.example.weatherapp.model.User;
import com.example.weatherapp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ユーザー登録処理を担当するコントローラークラス。
 * <p>
 * クライアントから送信された登録情報を元に新規ユーザーを作成し、
 * 重複チェックやバリデーションを行います。
 * </p>
 */
@RestController
@RequestMapping("/user")
public class RegisterController {

    /**
     * ユーザー情報を操作するリポジトリ。
     */
    private final UserRepository userRepository;

    /**
     * コンストラクタ。
     *
     * @param userRepository ユーザーリポジトリのインスタンス
     */
    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 新規ユーザー登録を行うPOSTエンドポイント。
     * <p>
     * リクエストボディのユーザー名とパスワードを検証し、
     * ユーザー名の重複がなければ新規ユーザーをDBに保存します。
     * バリデーションや重複チェックに失敗した場合は400エラーを返します。
     * </p>
     *
     * @param request 登録用リクエストデータ（ユーザー名・パスワード）
     * @return 登録結果を含むレスポンスエンティティ
     */
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody RegisterRequest request) {
        if (request.getUsername() == null || request.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("400 Bad Request", null));
        }

        if (userRepository.findByUsername(request.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("400 Bad Request", null));
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        userRepository.save(user);

        UserResponse userResponse = UserResponse.fromEntity(user);
        return ResponseEntity.ok(new ApiResponse<>("200 OK", userResponse));
    }
}
