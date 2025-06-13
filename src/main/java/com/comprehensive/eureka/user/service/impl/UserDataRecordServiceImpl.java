package com.comprehensive.eureka.user.service.impl;

import com.comprehensive.eureka.user.dto.request.UserDataRecordRequestDto;
import com.comprehensive.eureka.user.dto.response.UserDataRecordResponseDto;
import com.comprehensive.eureka.user.repository.UserDataRecordRepository;
import com.comprehensive.eureka.user.service.UserDataRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserDataRecordServiceImpl implements UserDataRecordService {
    private final UserDataRecordRepository userDataRecordRepository;

    @Override
    public List<UserDataRecordResponseDto> getUserUsage(UserDataRecordRequestDto userDataRecordRequestDto) {
        log.info("[getUserUsage] 사용자 사용량 조회 요청");
        return userDataRecordRepository.findUserUsage(userDataRecordRequestDto.getUserId(), userDataRecordRequestDto.getMonthCount());
    }
}
