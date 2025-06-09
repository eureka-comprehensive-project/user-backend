package com.comprehensive.eureka.user.controller;

import com.comprehensive.eureka.user.dto.base.BaseResponseDto;
import com.comprehensive.eureka.user.dto.request.GetByEmailRequestDto;
import com.comprehensive.eureka.user.dto.request.GetByIdRequestDto;
import com.comprehensive.eureka.user.dto.response.GetUserProfileDetailResponseDto;
import com.comprehensive.eureka.user.dto.response.GetUserProfileResponseDto;
import com.comprehensive.eureka.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/profile")
    public BaseResponseDto<GetUserProfileResponseDto> getUserProfile(@RequestBody GetByIdRequestDto getByIdRequestDto) {
        GetUserProfileResponseDto getUserProfileResponseDto = userService.getUserProfile(getByIdRequestDto);
        return BaseResponseDto.success(getUserProfileResponseDto);
    }

    @PostMapping("/profileDetail")
    public BaseResponseDto<GetUserProfileDetailResponseDto> getUserProfileDetail(@RequestBody GetByIdRequestDto getByIdRequestDto) {
        GetUserProfileDetailResponseDto getUserProfileDetailResponseDto = userService.getUserProfileDetail(getByIdRequestDto);
        return BaseResponseDto.success(getUserProfileDetailResponseDto);
    }

    @GetMapping("/{userId}/birthday")
    public BaseResponseDto<LocalDate> getUserBirthday(@PathVariable Long userId) {
        LocalDate birthday = userService.getUserBirthday(userId);
        return BaseResponseDto.success(birthday);
    }
}
