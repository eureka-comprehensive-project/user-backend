package com.comprehensive.eureka.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserDataRecordResponseDto {
    private Long userId;
    private Integer dataUsage;
    private String dataUsageUnit;
    private Integer messageUsage;
    private Integer callUsage;
    private String yearMonth;
}
