package com.example.weatherapp.controller;

import com.example.weatherapp.dto.PrefectureRequest;
import com.example.weatherapp.dto.PrefectureResponse;
import com.example.weatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/dashboard/{userId}")
    public ResponseEntity<?> getDashboard(@PathVariable Long userId) {
        PrefectureResponse prefectureData = weatherService.getDashboard(userId);

        return ResponseEntity.ok(
            Map.of(
                "status", "200 OK",
                "data", Map.of(
                    "id", prefectureData.getUserId()
                )
            )
        );
    }

    @PostMapping("/prefecture")
    public ResponseEntity<?> savePrefecture(@RequestBody PrefectureRequest request) {
        PrefectureResponse response = weatherService.savePrefecture(request);

        return ResponseEntity.ok(
            Map.of(
                "status", "200 OK",
                "prefecture", Map.of(
                    "code", response.getCode()
                )
            )
        );
    }

    @PutMapping("/prefecture")
    public ResponseEntity<?> updatePrefecture(@RequestBody PrefectureRequest request) {
        PrefectureResponse response = weatherService.updatePrefecture(request);

        return ResponseEntity.ok(
            Map.of(
                "status", "200 OK",
                "prefecture", Map.of(
                    "code", response.getCode()
                )
            )
        );
    }
}
