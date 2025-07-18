package com.example.weatherapp.controller;

import com.example.weatherapp.model.User;
import com.example.weatherapp.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * ログインやユーザー登録、ログアウトを扱うコントローラークラス。
 */
@Controller
public class LoginController {

    private final UserRepository userRepository;

    /**
     * コンストラクタ。
     * @param userRepository ユーザー情報にアクセスするリポジトリ
     */
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * ログインフォームの表示。
     * @return ログイン画面のテンプレート名
     */
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    /**
     * ログイン処理を行う。
     * @param username ユーザー名
     * @param password パスワード
     * @param session HTTPセッション
     * @param model モデル情報
     * @return ログイン成功時はダッシュボード、失敗時はログイン画面
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("userId", user.getId());
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "ユーザー名またはパスワードが違います");
            return "login";
        }
    }

    /**
     * ユーザー登録フォームの表示。
     * @return 登録画面のテンプレート名
     */
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    /**
     * 新規ユーザーを登録する。
     * @param username 登録するユーザー名
     * @param password 登録するパスワード
     * @param model モデル情報
     * @return 登録成功時はログイン画面、失敗時は登録画面
     */
    @PostMapping("/user")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           Model model) {
        if (userRepository.findByUsername(username) != null) {
            model.addAttribute("error", "このユーザー名は既に使われています");
            return "register";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);
        return "redirect:/login";
    }

    /**
     * ログアウト処理を行う。
     * @param session HTTPセッション
     * @return ログイン画面にリダイレクト
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}

