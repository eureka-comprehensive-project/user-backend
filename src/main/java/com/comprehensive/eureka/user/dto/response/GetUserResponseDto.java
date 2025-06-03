package com.comprehensive.eureka.user.dto.response;

import com.comprehensive.eureka.user.entity.User;
import com.comprehensive.eureka.user.entity.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserResponseDto {
    private String email; // 아이디에 사용될 값
    private String password; // 비밀번호 (암호화된 값)
    private Role role; // 역할 (일반사용자, 관리자)

    public static GetUserResponseDto from(User user) {
        return GetUserResponseDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}
