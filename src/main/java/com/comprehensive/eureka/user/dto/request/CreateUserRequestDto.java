package com.comprehensive.eureka.user.dto.request;

import com.comprehensive.eureka.user.entity.User;
import com.comprehensive.eureka.user.entity.enums.Role;
import com.comprehensive.eureka.user.entity.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CreateUserRequestDto {
    private String email; // 아이디에 사용될 값
    private String name; // 사용자 이름
    private String password; // 비밀번호 (암호화된 값)
    private LocalDate birthday; // 나이

    public static User toEntity(CreateUserRequestDto createUserRequestDto) {
        return User.builder()
                .email(createUserRequestDto.getEmail())
                .name(createUserRequestDto.getName())
                .password(createUserRequestDto.getPassword())
                .birthday(createUserRequestDto.getBirthday())
                .role(Role.ROLE_USER)
                .status(Status.ACTIVE)
                .build();

    }
}
