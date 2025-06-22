package com.comprehensive.eureka.user.dto.response;

import com.comprehensive.eureka.user.entity.User;
import com.comprehensive.eureka.user.entity.enums.Role;
import com.comprehensive.eureka.user.entity.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GetUserResponseDto {
    private Long userId; // 사용자 고유 아이디 값
    private String email; // 아이디에 사용될 값
    private String password; // 비밀번호 (암호화된 값)
    private Role role; // 역할 (일반사용자, 관리자)
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static GetUserResponseDto from(User user) {
        return GetUserResponseDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
