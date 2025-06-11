package com.comprehensive.eureka.user.service.impl;

import com.comprehensive.eureka.user.dto.request.UserDataRecordRequestDto;
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
    public List<UserDataRecordResponseDto> getUserUsage(UserDataRecordRequestDto userDataRecordRequestDto) {
        return userDataRecordRepository.findUserUsage(userDataRecordRequestDto.getUserId(), userDataRecordRequestDto.getMonthCount());
    }
}
