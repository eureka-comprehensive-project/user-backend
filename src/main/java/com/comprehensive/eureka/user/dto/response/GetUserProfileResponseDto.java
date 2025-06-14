package com.comprehensive.eureka.user.dto.response;

import com.comprehensive.eureka.user.entity.User;
import lombok.Builder;
import lombok.Data;

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
