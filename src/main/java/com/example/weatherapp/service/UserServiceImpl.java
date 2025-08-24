package com.example.weatherapp.service;

import com.example.weatherapp.dto.ApiResponse;
import com.example.weatherapp.dto.RegisterRequest;
import com.example.weatherapp.dto.UserResponse;
import com.example.weatherapp.model.User;
import com.example.weatherapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * ユーザー関連のサービス実装クラス
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * ユーザー登録処理
     *
     * @param request 登録リクエスト（ユーザ名、パスワード）
     * @return 登録結果レスポンス
     */
    @Override
    public ApiResponse<?> register(RegisterRequest request) {
        // --- 重複チェック ---
        Optional<User> existingUser = userRepository.findByUsername(request.getUsername());
        if (existingUser.isPresent()) {
            return ApiResponse.error("ユーザ名が既に存在するため登録できません");
        }

        // --- 新規登録処理 ---
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword()); // 実運用ならハッシュ化必須！
        userRepository.save(user);

        return ApiResponse.success(new UserResponse(user.getId(), user.getUsername()));
    }

    /**
     * ログイン処理
     *
     * @param username ユーザー名
     * @param password パスワード
     * @return ログイン結果レスポンス
     */
    @Override
    public ApiResponse<?> login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            User user = userOpt.get();
            return ApiResponse.success(new UserResponse(user.getId(), user.getUsername()));
        }
        return ApiResponse.error("ユーザ名またはパスワードが正しくありません");
    }
}
