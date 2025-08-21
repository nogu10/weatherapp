package com.example.weatherapp.service;

import com.example.weatherapp.model.User;
import com.example.weatherapp.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * ユーザー認証・登録のビジネスロジック実装
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){ this.userRepository = userRepository; }

    @Override
    public User login(String username, String password){
        User user = userRepository.findByUsername(username);
        if(user != null && user.getPassword().equals(password)){
            return user;
        }
        return null;
    }

    @Override
    public User register(String username, String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return userRepository.save(user);
    }
}
