package com.example.weatherapp.controller;

import com.example.weatherapp.model.User;
import com.example.weatherapp.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * ログイン・ユーザー登録・ログアウトなど、
 * セッションを利用した認証系の処理を担当するコントローラー。
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * ログインフォーム画面の表示
     *
     * @return login.html のビュー名
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    /**
     * ログイン処理を実行し、成功すればセッションにユーザー情報を保存する
     *
     * @param username フォームから送信されたユーザー名
     * @param password フォームから送信されたパスワード
     * @param session  現在の HTTP セッション
     * @param model    Thymeleaf に渡す Model オブジェクト
     * @return 成功: dashboard.html にリダイレクト、失敗: login.html に戻る
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        User user = userService.login(username, password);
        if (user != null) {
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "ユーザー名またはパスワードが正しくありません。");
            return "login";
        }
    }

    /**
     * 新規登録フォーム画面の表示
     *
     * @return register.html のビュー名
     */
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    /**
     * ユーザー登録処理を実行し、登録が成功したらログイン状態にしてダッシュボードへ遷移
     *
     * @param username フォームから送信されたユーザー名
     * @param password フォームから送信されたパスワード
     * @param session  現在の HTTP セッション
     * @param model    Thymeleaf に渡す Model オブジェクト
     * @return 成功: dashboard にリダイレクト、失敗: register.html に戻る
     */
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           HttpSession session,
                           Model model) {
        try {
            User user = userService.register(username, password);
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            return "redirect:/dashboard";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    /**
     * ログアウト処理。セッションを破棄し、ログイン画面へリダイレクトする
     *
     * @param session 現在の HTTP セッション
     * @return login 画面へのリダイレクト
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
