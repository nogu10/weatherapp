package com.example.weatherapp.controller;

import com.example.weatherapp.model.Prefecture;
import com.example.weatherapp.model.PrefectureUpdateRequest;
import com.example.weatherapp.model.User;
import com.example.weatherapp.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Controller
public class WeatherController {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${weather.api.key}")
    private String apiKey;

    public WeatherController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 都道府県選択画面の表示
    @GetMapping("/select-prefecture")
    public String showPrefectureForm(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        model.addAttribute("prefectures", Prefecture.values());
        return "select_prefecture";
    }

    // 都道府県選択処理（画面からPOST）
    @PostMapping("/prefecture")
    public String handleSelectPrefecture(@RequestParam int prefectureCode, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                Prefecture selected = Prefecture.fromCode(prefectureCode); // コードからEnum変換
                user.setSelectedPrefecture(selected);
                userRepository.save(user);
            }
        }
        return "redirect:/dashboard";
    }

    // 天気情報表示
    @GetMapping("/dashboard")
    public String showWeather(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Prefecture selected = user.getSelectedPrefecture();

            if (selected == null) {
                return "redirect:/select-prefecture";
            }

            String prefectureName = selected.getLabel(); // 例：「東京」
            String url = "https://api.openweathermap.org/data/2.5/weather?q=" +
                    prefectureName + ",JP&appid=" + apiKey + "&units=metric&lang=ja";

            try {
                Map<String, Object> weatherData = restTemplate.getForObject(url, Map.class);
                model.addAttribute("user", user);
                model.addAttribute("prefecture", prefectureName);
                model.addAttribute("weather", weatherData);
                return "weather";
            } catch (Exception e) {
                model.addAttribute("error", "天気情報の取得に失敗しました");
            }
        }

        return "redirect:/login";
    }

    // ✅ 都道府県の更新処理（PUTリクエスト、JSON形式）
    @PutMapping("/user/prefecture")
    @ResponseBody
    public String updateUserPrefecture(@RequestBody PrefectureUpdateRequest request) {
        Optional<User> optionalUser = userRepository.findById(request.getUserId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Prefecture prefecture = Prefecture.fromCode(request.getPrefectureCode());
            user.setSelectedPrefecture(prefecture);
            userRepository.save(user);
            return "都道府県を更新しました";
        } else {
            return "ユーザーが見つかりません";
        }
    }
}
