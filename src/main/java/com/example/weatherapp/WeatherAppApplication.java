package com.example.weatherapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot アプリケーションのメインエントリーポイント。
 */
@SpringBootApplication
public class WeatherAppApplication {

    /**
     * アプリケーションを起動します。
     *
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        SpringApplication.run(WeatherAppApplication.class, args);
    }
}
