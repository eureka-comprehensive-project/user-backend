package com.comprehensive.eureka.user.dto.response;

import com.comprehensive.eureka.user.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class GetUserProfileDetailResponseDto {
    private String email;
    private String name;
    private String phone;
    private LocalDate birthday;
    private LocalDateTime createdAt;

    public static GetUserProfileDetailResponseDto from(User user) {
        return GetUserProfileDetailResponseDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .birthday(user.getBirthday())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
