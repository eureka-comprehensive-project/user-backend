package com.comprehensive.eureka.user.dto.request;

import com.comprehensive.eureka.user.entity.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateUserStatusRequestDto {
    private Long userId;
    private Status status;
    private LocalDateTime unbanTime;
}
