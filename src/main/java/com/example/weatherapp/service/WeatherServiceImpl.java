package com.example.weatherapp.service;

import com.example.weatherapp.api.PrefectureRequest;
import com.example.weatherapp.api.PrefectureResponse;
import com.example.weatherapp.model.Prefecture;
import com.example.weatherapp.model.User;
import com.example.weatherapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * 天気サービス実装。
 * OpenWeather API から現在の天気情報を取得し、ユーザーのダッシュボード用に整形する。
 */
@Service
public class WeatherServiceImpl implements WeatherService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    /** OpenWeather API Key */
    @Value("${openweather.api.key}")
    private String apiKey;

    public WeatherServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public PrefectureResponse getDashboard(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || user.getSelectedPrefecture() == null) return null;

        Prefecture prefecture = user.getSelectedPrefecture();

        // OpenWeather API 呼び出し
        String url = String.format(
                "https://api.openweathermap.org/data/2.5/weather?q=%s,jp&units=metric&appid=%s",
                prefecture.getLabel(), apiKey);

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        if (response == null || response.get("main") == null) return null;

        Map<String, Object> main = (Map<String, Object>) response.get("main");
        Double temp = main.get("temp") != null ? ((Number) main.get("temp")).doubleValue() : null;

        // 天気情報（weather配列のmainを取得）
        String weatherStr = null;
        if (response.get("weather") instanceof java.util.List list && !list.isEmpty()) {
            Map<String, Object> weatherMap = (Map<String, Object>) list.get(0);
            weatherStr = weatherMap.get("main") != null ? weatherMap.get("main").toString() : null;
        }

        return new PrefectureResponse(
                user.getId(),
                prefecture.name(),
                temp,
                weatherStr,
                LocalDate.now().toString()
        );
    }

    @Override
    public PrefectureResponse savePrefecture(PrefectureRequest request) {
        User user = userRepository.findById(request.getUserId()).orElse(null);
        if (user == null) return null;

        try {
            Prefecture prefecture = Prefecture.fromCode(request.getPrefecture());
            user.setSelectedPrefecture(prefecture);
            userRepository.save(user);
            return getDashboard(user.getId());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public PrefectureResponse updatePrefecture(PrefectureRequest request) {
        return savePrefecture(request);
    }
}
