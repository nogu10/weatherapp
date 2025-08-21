package com.example.weatherapp.repository;

import com.example.weatherapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ユーザー情報のデータアクセスリポジトリ
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
