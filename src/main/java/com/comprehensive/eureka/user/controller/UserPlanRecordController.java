package com.comprehensive.eureka.user.controller;

import com.comprehensive.eureka.user.dto.base.BaseResponseDto;
import com.comprehensive.eureka.user.dto.response.UserActivePlanBenefitResponseDto;
import com.comprehensive.eureka.user.service.UserPlanRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserPlanRecordController {
    private final UserPlanRecordService userPlanRecordService;

    @PostMapping("/user-plan-record/valid-contract")
    public BaseResponseDto<List<UserActivePlanBenefitResponseDto>> getActivePlanBenefits(@RequestBody List<Long> userIds) {
        List<UserActivePlanBenefitResponseDto> result = userPlanRecordService.getActivePlanBenefits(userIds);
        return BaseResponseDto.success(result);
    }
}
