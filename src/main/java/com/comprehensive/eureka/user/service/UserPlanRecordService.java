package com.comprehensive.eureka.user.service;

import com.comprehensive.eureka.user.dto.request.CreateUserPlanRecordRequestDto;
import com.comprehensive.eureka.user.dto.response.UserActivePlanBenefitResponseDto;

import java.util.List;

public interface UserPlanRecordService {
    List<UserActivePlanBenefitResponseDto> getActivePlanBenefits(List<Long> userIds);

    void createUserPlanRecord(CreateUserPlanRecordRequestDto createUserPlanRecordRequestDto);
}
