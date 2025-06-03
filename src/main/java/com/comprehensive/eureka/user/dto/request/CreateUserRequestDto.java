package com.comprehensive.eureka.user.dto.request;

import com.comprehensive.eureka.user.entity.User;
import com.comprehensive.eureka.user.entity.enums.Role;
import com.comprehensive.eureka.user.entity.enums.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequestDto {
    private String email; // 아이디에 사용될 값
    private String name; // 사용자 이름
    private String password; // 비밀번호 (암호화된 값)
    private Integer age; // 나이
    private Boolean isGuardian; // 보호자 여부

    public static User toEntity(CreateUserRequestDto createUserRequestDto) {
        return User.builder()
                .email(createUserRequestDto.getEmail())
                .name(createUserRequestDto.getName())
                .password(createUserRequestDto.getPassword())
                .age(createUserRequestDto.getAge())
                .isGuardian(createUserRequestDto.getIsGuardian())
                .role(Role.ROLE_USER)
                .status(Status.ACTIVE)
                .build();

    }
}
