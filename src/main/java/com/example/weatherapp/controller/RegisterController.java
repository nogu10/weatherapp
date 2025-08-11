package com.example.weatherapp.controller;

import com.example.weatherapp.api.ApiResponse;
import com.example.weatherapp.api.RegisterRequest;
import com.example.weatherapp.api.UserResponse;
import com.example.weatherapp.model.User;
import com.example.weatherapp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ユーザー登録用のRESTコントローラークラス。
 * <p>
 * クライアントから送信された新規登録情報を受け取り、
 * ユーザー名の重複チェックやバリデーションを行い、
 * 新規ユーザーをデータベースに保存します。
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
     * コンストラクタによるUserRepositoryの注入。
     *
     * @param userRepository ユーザーリポジトリ
     */
    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 新規ユーザー登録用POSTエンドポイント。
     * <p>
     * リクエストボディで送られたユーザー名とパスワードを検証し、
     * ユーザー名が既に存在しない場合に新規ユーザーをDBに保存します。
     * 入力不備や重複があれば400 Bad Requestを返します。
     * </p>
     *
     * @param request 新規登録のユーザー情報（ユーザー名・パスワード）
     * @return 登録結果のAPIレスポンス。成功時は200 OKとユーザー情報を返す。
     */
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody RegisterRequest request) {
        // 入力チェック：ユーザー名またはパスワードがnullなら400エラー
        if (request.getUsername() == null || request.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("400 Bad Request", null));
        }

        // ユーザー名の重複チェック
        if (userRepository.findByUsername(request.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("400 Bad Request", null));
        }

        // 新規ユーザーエンティティを作成し、ユーザー名とパスワードを設定
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        // データベースに保存
        userRepository.save(user);

        // エンティティからレスポンスDTOを生成
        UserResponse userResponse = UserResponse.fromEntity(user);

        // 200 OKとともにユーザー情報を返す
        return ResponseEntity.ok(new ApiResponse<>("200 OK", userResponse));
    }
}
