package com.comprehensive.eureka.user.dto.request;

import lombok.Data;

@Data
public class CreateUserPlanRecordRequestDto {
    private Long userId;
    private Long planBenefitId;
}
