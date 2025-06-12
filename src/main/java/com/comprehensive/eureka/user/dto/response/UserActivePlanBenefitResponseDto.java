package com.comprehensive.eureka.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserActivePlanBenefitResponseDto {
    private Long userId;
    private Long planBenefitId;
}
