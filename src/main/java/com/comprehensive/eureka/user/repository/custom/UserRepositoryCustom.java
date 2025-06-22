package com.comprehensive.eureka.user.repository.custom;

import com.comprehensive.eureka.user.dto.response.UserInfoResponseDto;
import com.comprehensive.eureka.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<User> findUserByEmailAndStatus(String email);
    Optional<User> findOAuthUserByEmail(String email);
    List<UserInfoResponseDto> searchUsersBySearchWord(String searchWord);
    long bulkUnbanUsers(LocalDateTime now);
}
