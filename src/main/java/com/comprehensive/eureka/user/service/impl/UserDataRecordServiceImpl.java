package com.comprehensive.eureka.user.service.impl;

import com.comprehensive.eureka.user.dto.request.CreateUserDataRecordRequestDto;
import com.comprehensive.eureka.user.dto.request.UserDataRecordRequestDto;
import com.comprehensive.eureka.user.dto.response.UserDataRecordResponseDto;
import com.comprehensive.eureka.user.entity.User;
import com.comprehensive.eureka.user.entity.UserDataRecord;
import com.comprehensive.eureka.user.exception.UserNotFoundException;
import com.comprehensive.eureka.user.repository.UserDataRecordRepository;
import com.comprehensive.eureka.user.repository.UserRepository;
import com.comprehensive.eureka.user.service.UserDataRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserDataRecordServiceImpl implements UserDataRecordService {
    private final UserDataRecordRepository userDataRecordRepository;
    private final UserRepository userRepository;

    @Override
    public List<UserDataRecordResponseDto> getUserUsage(UserDataRecordRequestDto userDataRecordRequestDto) {
        return userDataRecordRepository.findUserUsage(userDataRecordRequestDto.getUserId(), userDataRecordRequestDto.getMonthCount());
    }

    @Override
    public void createUserDataRecord(CreateUserDataRecordRequestDto createUserDataRecordRequestDto) {
        Long userId = createUserDataRecordRequestDto.getUserId();
        log.info("사용자 사용량 기록 저장 요청 - userId: {}", userId);

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        userDataRecordRepository.save(UserDataRecord.builder()
                .user(user)
                .dataUsage(createUserDataRecordRequestDto.getDataUsage())
                .dataUsageUnit(createUserDataRecordRequestDto.getDataUsageUnit())
                .messageUsage(createUserDataRecordRequestDto.getMessageUsage())
                .callUsage(createUserDataRecordRequestDto.getCallUsage())
                .yearMonth(createUserDataRecordRequestDto.getYearMonth())
                .build());

        log.info("사용량 기록 저장 완료 - userId: {}", userId);
    }
}
