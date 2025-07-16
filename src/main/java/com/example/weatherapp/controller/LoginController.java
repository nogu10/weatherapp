package com.example.weatherapp.controller;

import com.example.weatherapp.model.User;
import com.example.weatherapp.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ログインフォーム表示
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // ログイン処理
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("userId", user.getId());
            return "redirect:/dashboard";  // weather → dashboard に合わせました
        } else {
            model.addAttribute("error", "ユーザー名またはパスワードが違います");
            return "login";
        }
    }

    // ユーザー登録フォーム表示
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    // ユーザー登録処理
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

    // ✅ ログアウト処理
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // セッション破棄
        return "redirect:/login";
    }
}
