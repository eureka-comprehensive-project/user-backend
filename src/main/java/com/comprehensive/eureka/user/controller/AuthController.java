package com.comprehensive.eureka.user.controller;

import com.comprehensive.eureka.user.dto.request.CreateUserRequestDto;
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

    @GetMapping("/{email}")
    public BaseResponseDto<GetUserResponseDto> getByEmail(@PathVariable String email) {
        GetUserResponseDto getUserResponseDto = userService.findUserByEmail(email);
        return BaseResponseDto.success(getUserResponseDto);
    }
}
