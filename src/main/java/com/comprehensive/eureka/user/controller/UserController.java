package com.comprehensive.eureka.user.controller;

import com.comprehensive.eureka.user.dto.base.BaseResponseDto;
import com.comprehensive.eureka.user.dto.request.GetByEmailRequestDto;
import com.comprehensive.eureka.user.dto.request.GetByIdRequestDto;
import com.comprehensive.eureka.user.dto.request.UpdateUserStatusRequestDto;
import com.comprehensive.eureka.user.dto.response.GetUserProfileDetailResponseDto;
import com.comprehensive.eureka.user.dto.response.GetUserProfileResponseDto;
import com.comprehensive.eureka.user.dto.response.UserInfoResponseDto;
import com.comprehensive.eureka.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
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

    @PutMapping("/{userId}/status")
    public BaseResponseDto<Void> updateUserStatus(@PathVariable Long userId){
        userService.updateUserStatus(userId);
        return BaseResponseDto.success(null);
    }

    @GetMapping("/search")
    public BaseResponseDto<List<UserInfoResponseDto>> searchUsers(@RequestParam String searchWord) {
        List<UserInfoResponseDto> users = userService.searchUsers(searchWord);
        return BaseResponseDto.success(users);
    }

    @PutMapping("/status")
    public BaseResponseDto<Void> updateUserStatusAndTime(@RequestBody UpdateUserStatusRequestDto updateUserStatusRequestDto) {
        userService.updateUserStatusAndTime(updateUserStatusRequestDto);
        return BaseResponseDto.success(null);
    }

    @PutMapping("/status-active")
    public BaseResponseDto<Void> updateUserStatusActive(@RequestBody GetByEmailRequestDto getByEmailRequestDto){
        userService.updateUserStatusActive(getByEmailRequestDto);
        return BaseResponseDto.success(null);
    }
}
