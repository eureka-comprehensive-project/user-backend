package com.comprehensive.eureka.user.service.impl;

import com.comprehensive.eureka.user.dto.response.UserDataRecordResponseDto;
import com.comprehensive.eureka.user.repository.UserDataRecordRepository;
import com.comprehensive.eureka.user.service.UserDataRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDataRecordServiceImpl implements UserDataRecordService {
    private final UserDataRecordRepository userDataRecordRepository;

    @Override
    public List<UserDataRecordResponseDto> findRecentDataUsageByUserId(Long userId) {
        return userDataRecordRepository.findRecentDataUsageByUserId(userId);
    }
}
