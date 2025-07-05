package com.example.weatherapp.controller;

import com.example.weatherapp.model.Prefecture;
import com.example.weatherapp.model.User;
import com.example.weatherapp.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class WeatherController {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${weather.api.key}")
    private String apiKey;

    public WeatherController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/select-prefecture")
    public String selectPrefecture(Model model) {
        model.addAttribute("prefectures", Prefecture.values());
        return "select-prefecture";
    }

    @PostMapping("/select-prefecture")
    public String handleSelectPrefecture(@RequestParam String prefecture, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return "redirect:/login";
        }
        user.setSelectedPrefecture(Prefecture.valueOf(prefecture));
        userRepository.save(user);
        return "redirect:/weather";
    }

    @GetMapping("/weather")
    public String showWeather(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return "redirect:/login";
        }
        Prefecture pref = user.getSelectedPrefecture();
        if (pref == null) {
            return "redirect:/select-prefecture";
        }

        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + pref + ",JP&appid=" + apiKey + "&units=metric&lang=ja";

        String weatherJson = restTemplate.getForObject(url, String.class);
        model.addAttribute("weatherJson", weatherJson);
        model.addAttribute("prefecture", pref);
        return "weather";
    }
}
