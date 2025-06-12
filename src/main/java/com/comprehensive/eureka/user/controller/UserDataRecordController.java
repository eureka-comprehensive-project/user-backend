package com.comprehensive.eureka.user.controller;

import com.comprehensive.eureka.user.dto.base.BaseResponseDto;
import com.comprehensive.eureka.user.dto.request.CreateUserDataRecordRequestDto;
import com.comprehensive.eureka.user.dto.request.UserDataRecordRequestDto;
import com.comprehensive.eureka.user.dto.response.GetUserProfileDetailResponseDto;
import com.comprehensive.eureka.user.dto.response.UserDataRecordResponseDto;
import com.comprehensive.eureka.user.entity.UserDataRecord;
import com.comprehensive.eureka.user.service.UserDataRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserDataRecordController {
    private final UserDataRecordService userDataRecordService;

    @PostMapping("/user-data-record/usage")
    public BaseResponseDto<List<UserDataRecordResponseDto>> getUserUsage(@RequestBody UserDataRecordRequestDto userDataRecordRequestDto) {
        return BaseResponseDto.success(userDataRecordService.getUserUsage(userDataRecordRequestDto));
    }

    @PostMapping("/user-data-record")
    public BaseResponseDto<Void> createUserDataRecord(@RequestBody CreateUserDataRecordRequestDto createUserDataRecordRequestDto) {
        userDataRecordService.createUserDataRecord(createUserDataRecordRequestDto);
        return BaseResponseDto.success(null);
    }
}
