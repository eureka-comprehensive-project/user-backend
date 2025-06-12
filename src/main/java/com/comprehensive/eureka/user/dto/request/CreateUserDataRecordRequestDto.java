package com.comprehensive.eureka.user.dto.request;

import lombok.Data;

@Data
public class CreateUserDataRecordRequestDto {
    private Long userId;
    private Integer dataUsage;
    private String dataUsageUnit;
    private Integer messageUsage;
    private Integer callUsage;
    private String yearMonth;
}
