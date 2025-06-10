package com.comprehensive.eureka.user.controller;

import com.comprehensive.eureka.user.dto.base.BaseResponseDto;
import com.comprehensive.eureka.user.dto.response.GetUserProfileDetailResponseDto;
import com.comprehensive.eureka.user.dto.response.UserDataRecordResponseDto;
import com.comprehensive.eureka.user.entity.UserDataRecord;
import com.comprehensive.eureka.user.service.UserDataRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/user-data-record")
@RestController
@RequiredArgsConstructor
public class UserDataRecordController {
    private final UserDataRecordService userDataRecordService;

    @GetMapping("/{userId}/data-usage")
    public BaseResponseDto<List<UserDataRecordResponseDto>> getDataUsage(@PathVariable Long userId) {
        return BaseResponseDto.success(userDataRecordService.findRecentDataUsageByUserId(userId));
    }
}
