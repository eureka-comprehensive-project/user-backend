package com.comprehensive.eureka.user.repository;

import com.comprehensive.eureka.user.entity.User;
import com.comprehensive.eureka.user.repository.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByEmail(String email);
}
