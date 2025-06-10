package com.comprehensive.eureka.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDataRecordResponseDto {
    private Long userId;
    private Integer dataUsage;
    private String dataUsageUnit;
    private String yearMonth;
}
