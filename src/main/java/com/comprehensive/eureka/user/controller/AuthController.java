package com.comprehensive.eureka.user.controller;

import com.comprehensive.eureka.user.dto.request.CreateUserRequestDto;
import com.comprehensive.eureka.user.dto.request.GetByEmailRequestDto;
import com.comprehensive.eureka.user.dto.response.CreateUserResponseDto;
import com.comprehensive.eureka.user.dto.response.GetUserResponseDto;
import com.comprehensive.eureka.user.dto.base.BaseResponseDto;
import com.comprehensive.eureka.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/")
    public BaseResponseDto<CreateUserResponseDto> createUser(@RequestBody CreateUserRequestDto createUserRequestDto) {
        CreateUserResponseDto createUserResponseDto = userService.createUser(createUserRequestDto);
        return BaseResponseDto.success(createUserResponseDto);
    }

    @PostMapping("/email")
    public BaseResponseDto<GetUserResponseDto> getByEmail(@RequestBody GetByEmailRequestDto getByEmailRequest) {
        GetUserResponseDto getUserResponseDto = userService.findUserByEmail(getByEmailRequest);
        return BaseResponseDto.success(getUserResponseDto);
    }

    @PostMapping("/email-check")
    public BaseResponseDto<Boolean> emailCheck(@RequestBody GetByEmailRequestDto getByEmailRequestDto){
        boolean exists = userService.emailExists(getByEmailRequestDto);
        return BaseResponseDto.success(exists); // 존재하면 true, 아니면 false
    }
}
