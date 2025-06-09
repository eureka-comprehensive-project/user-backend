package com.comprehensive.eureka.user.dto.response;

import com.comprehensive.eureka.user.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class GetUserProfileResponseDto {
    private String name;
    private String phone;

    public static GetUserProfileResponseDto from(User user) {
        return GetUserProfileResponseDto.builder()
                .name(user.getName())
                .phone(user.getPhone())
                .build();
    }
}
