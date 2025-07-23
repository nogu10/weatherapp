package com.example.weatherapp.controller;

import com.example.weatherapp.enums.Prefecture;
import com.example.weatherapp.model.User;
import com.example.weatherapp.repository.UserRepository;
import com.example.weatherapp.service.WeatherService;
import com.example.weatherapp.service.dto.WeatherResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ダッシュボード画面で天気情報を表示するコントローラー。
 */
@Controller
@RequestMapping("/dashboard")
public class WeatherController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;

    /**
     * ダッシュボード画面を表示し、選択された都道府県の天気情報を取得して表示する。
     *
     * @param session ユーザーのセッション情報
     * @param model   HTMLテンプレートへデータを渡すためのModel
     * @return ダッシュボード画面のテンプレート名
     */
    @GetMapping
    public String showDashboard(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        User user = userRepository.findByUsername(username);
        if (user == null || user.getPrefecture() == null) {
            return "redirect:/prefecture";
        }

        Prefecture prefecture = user.getPrefecture();
        WeatherResponse weather = weatherService.getWeather(prefecture);

        model.addAttribute("username", username);
        model.addAttribute("prefecture", prefecture.getLabel());
        model.addAttribute("weather", weather);

        return "dashboard";
    }
}
