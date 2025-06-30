package com.example.weatherapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * ユーザーの画面遷移と操作を処理するコントローラークラス。
 * ログイン・都道府県選択・天気表示を制御します。
 */
@Controller
public class WeatherController {

    /**
     * ログイン画面を表示します。
     *
     * @return login.html のテンプレート名
     */
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    /**
     * 都道府県選択画面を表示します。
     *
     * @return select.html のテンプレート名
     */
    @GetMapping("/select")
    public String showSelectForm() {
        return "select";
    }

    /**
     * 選ばれた都道府県の天気を取得し、結果を表示します。
     *
     * @param prefecture ユーザーが選択した都道府県名
     * @return result.html のテンプレート名
     */
    @PostMapping("/select-prefecture")
    public String handleSelection(@RequestParam String prefecture) {
        return "result";
    }
}
