package com.example.weatherapp.controller;

import com.example.weatherapp.enums.Prefecture;
import com.example.weatherapp.model.User;
import com.example.weatherapp.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 都道府県の選択および変更を処理するコントローラークラス。
 */
@Controller
@RequestMapping("/prefecture")
public class PrefectureController {

    @Autowired
    private UserRepository userRepository;

    /**
     * 都道府県の選択画面を表示する。
     *
     * @param model   HTMLテンプレートへデータを渡すためのModel
     * @param session ログイン中のユーザー情報を保持するHttpSession
     * @return 都道府県選択画面のテンプレート名
     */
    @GetMapping
    public String showPrefectureSelection(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        model.addAttribute("prefectures", Prefecture.values());
        return "select-prefecture";
    }

    /**
     * ユーザーが選択した都道府県を保存する。
     *
     * @param prefecture 選択された都道府県（enum）
     * @param session    ログイン中のユーザー情報
     * @return ダッシュボード画面へリダイレクト
     */
    @PostMapping
    public String handleSelectPrefecture(@RequestParam("prefecture") Prefecture prefecture,
                                         HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setPrefecture(prefecture);
            userRepository.save(user);
        }

        return "redirect:/dashboard";
    }
}
