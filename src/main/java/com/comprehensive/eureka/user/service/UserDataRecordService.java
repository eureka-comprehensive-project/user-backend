package com.comprehensive.eureka.user.service;

import com.comprehensive.eureka.user.dto.response.UserDataRecordResponseDto;

import java.util.List;

public interface UserDataRecordService {
    List<UserDataRecordResponseDto> findRecentDataUsageByUserId(Long userId);
}
