package com.comprehensive.eureka.user.repository.custom;

import com.comprehensive.eureka.user.dto.response.UserDataRecordResponseDto;

import java.util.List;

public interface UserDataRecordRepositoryCustom {
    List<UserDataRecordResponseDto> findRecentDataUsageByUserId(Long userId);
}
