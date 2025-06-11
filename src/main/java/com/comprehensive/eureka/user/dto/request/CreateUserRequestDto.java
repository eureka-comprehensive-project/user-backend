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
    private String email;
    private String name;
    private String password; // 비밀번호 (암호화된 값)
    private LocalDate birthday;
    private String phone;

    public static User toEntity(CreateUserRequestDto createUserRequestDto) {
        return User.builder()
                .email(createUserRequestDto.getEmail())
                .name(createUserRequestDto.getName())
                .password(createUserRequestDto.getPassword())
                .birthday(createUserRequestDto.getBirthday())
                .phone(createUserRequestDto.getPhone())
                .role(Role.ROLE_USER)
                .status(Status.ACTIVE)
                .build();

    }
}
