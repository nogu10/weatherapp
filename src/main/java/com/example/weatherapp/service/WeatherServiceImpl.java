package com.example.weatherapp.service;

import com.example.weatherapp.dto.PrefectureResponse;
import com.example.weatherapp.model.Prefecture;
import com.example.weatherapp.model.User;
import com.example.weatherapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 天気情報を取得するサービスの実装クラス
 */
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${openweather.api.key}")
    private String apiKey;

    /**
     * ダッシュボード用の現在の天気情報を取得
     *
     * @param userId ユーザーID
     * @return 天気情報レスポンス
     */
    @Override
    public PrefectureResponse getDashboard(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) return null;

        User user = userOpt.get();

        // --- 事前に選択された都道府県を取得 ---
        Prefecture prefecture = user.getSelectedPrefecture();
        if (prefecture == null) return null;

        // --- OpenWeather API 呼び出し ---
        String url = String.format(
                "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric",
                prefecture.getLabel(), apiKey
        );

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        if (response == null) return null;

        // --- response.get("main") を変数に格納して使い回す ---
        Object mainObj = response.get("main");
        if (!(mainObj instanceof Map<?, ?> mainMap)) return null;

        // 気温
        double temp = ((Number) mainMap.get("temp")).doubleValue();

        // 天気情報（weather配列の main を取得）
        String weatherStr = null;
        Object weatherObj = response.get("weather");
        if (weatherObj instanceof List<?> list && !list.isEmpty()) {
            Map<?, ?> weatherMap = (Map<?, ?>) list.get(0);
            weatherStr = (String) weatherMap.get("main");
        }

        // --- PrefectureResponse を返却 ---
        return new PrefectureResponse(
                prefecture.getCode(),
                prefecture.getLabel(),
                temp,
                weatherStr
        );
    }
}
