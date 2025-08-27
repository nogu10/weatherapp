package com.example.weatherapp.controller;

import com.example.weatherapp.dto.ApiResponse;
import com.example.weatherapp.dto.LoginRequest;
import com.example.weatherapp.dto.UserResponse;
import com.example.weatherapp.model.User;
import com.example.weatherapp.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ログイン用コントローラー
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService){ this.userService = userService; }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> login(@RequestBody LoginRequest request, HttpSession session){
        if(request.getUsername()==null || request.getPassword()==null){
            return ResponseEntity.badRequest().body(new ApiResponse<>("400 Bad Request", null));
        }
        User user = userService.login(request.getUsername(), request.getPassword());
        if(user == null){
            return ResponseEntity.status(401).body(new ApiResponse<>("401 Unauthorized", null));
        }
        session.setAttribute("userId", user.getId());
        session.setAttribute("username", user.getUsername());
        return ResponseEntity.ok(new ApiResponse<>("200 OK", UserResponse.fromEntity(user)));
    }
}
