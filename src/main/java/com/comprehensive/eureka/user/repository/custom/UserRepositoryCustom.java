package com.comprehensive.eureka.user.repository.custom;

import com.comprehensive.eureka.user.entity.User;

import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<User> findUserByEmailAndStatus(String email);
}
