package com.example.weatherapp.controller;

import com.example.weatherapp.dto.ApiResponse;
import com.example.weatherapp.dto.RegisterRequest;
import com.example.weatherapp.dto.UserResponse;
import com.example.weatherapp.model.User;
import com.example.weatherapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ユーザー登録・情報管理用コントローラー
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){ this.userService = userService; }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody RegisterRequest request){
        if(request.getUsername()==null || request.getPassword()==null){
            return ResponseEntity.badRequest().body(new ApiResponse<>("400 Bad Request", null));
        }
        User user = userService.register(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(new ApiResponse<>("200 OK", UserResponse.fromEntity(user)));
    }
}
