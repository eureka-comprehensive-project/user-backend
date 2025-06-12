package com.comprehensive.eureka.user.service;

import com.comprehensive.eureka.user.dto.request.CreateUserDataRecordRequestDto;
import com.comprehensive.eureka.user.dto.request.UserDataRecordRequestDto;
import com.comprehensive.eureka.user.dto.response.UserDataRecordResponseDto;

import java.util.List;

public interface UserDataRecordService {
    List<UserDataRecordResponseDto> getUserUsage(UserDataRecordRequestDto userDataRecordRequestDto);

    void createUserDataRecord(CreateUserDataRecordRequestDto createUserDataRecordRequestDto);
}
