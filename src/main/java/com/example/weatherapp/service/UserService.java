package com.example.weatherapp.service;

import com.example.weatherapp.model.User;

public interface UserService {
    User login(String username, String password);
    User register(String username, String password);
}
