package com.example.weatherapp.repository;

import com.example.weatherapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ユーザー情報に対するデータアクセスを担当するリポジトリインターフェース。
 * Spring Data JPA の {@link JpaRepository} を継承しています。
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * ユーザー名でユーザー情報を取得します。
     *
     * @param username ユーザー名
     * @return 該当するユーザーが存在しない場合は {@code null} を返します。
     */
    User findByUsername(String username);
}

