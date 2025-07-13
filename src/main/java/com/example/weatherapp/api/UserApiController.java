package com.example.weatherapp.api;

import com.example.weatherapp.model.Prefecture;
import com.example.weatherapp.model.User;
import com.example.weatherapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserApiController {

    @Autowired
    private UserRepository userRepository;

    @PutMapping("/prefecture")
    @Transactional
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
