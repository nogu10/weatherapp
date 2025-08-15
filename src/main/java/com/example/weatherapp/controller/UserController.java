package com.example.weatherapp.controller;

import com.example.weatherapp.api.ApiResponse;
import com.example.weatherapp.api.LoginRequest;
import com.example.weatherapp.api.RegisterRequest;
import com.example.weatherapp.api.UserResponse;
import com.example.weatherapp.model.User;
import com.example.weatherapp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ユーザー関連の処理を担当するRESTコントローラー。
 * 新規登録、ログイン、ユーザー情報取得を提供します。
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    /**
     * コンストラクタによる依存注入。
     */
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 新規ユーザー登録API
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody RegisterRequest request) {
        // 入力チェック
        if (request.getUsername() == null || request.getUsername().isBlank()
                || request.getPassword() == null || request.getPassword().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("400 Bad Request - Invalid input", null));
        }

        // ユーザー名重複チェック
        if (userRepository.findByUsername(request.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("400 Bad Request - Username already exists", null));
        }

        // ユーザー作成
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword()); // 実運用ではパスワードをハッシュ化すること
        userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse<>("200 OK", UserResponse.fromEntity(user)));
    }

    /**
     * ログインAPI
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponse>> login(@RequestBody LoginRequest request) {
        // 入力チェック
        if (request.getUsername() == null || request.getUsername().isBlank()
                || request.getPassword() == null || request.getPassword().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("400 Bad Request - Invalid input", null));
        }

        // ユーザー取得
        User user = userRepository.findByUsername(request.getUsername());
        if (user == null || !user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>("401 Unauthorized - Invalid credentials", null));
        }

        return ResponseEntity.ok(new ApiResponse<>("200 OK", UserResponse.fromEntity(user)));
    }

    /**
     * ユーザー情報取得API
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("404 Not Found - User not found", null));
        }
        return ResponseEntity.ok(new ApiResponse<>("200 OK", UserResponse.fromEntity(user)));
    }
}
