package com.comprehensive.eureka.user.service;

import com.comprehensive.eureka.user.dto.request.CreateUserRequestDto;
import com.comprehensive.eureka.user.dto.request.GetByEmailRequestDto;
import com.comprehensive.eureka.user.dto.request.GetByIdRequestDto;
import com.comprehensive.eureka.user.dto.response.*;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    CreateUserResponseDto createUser(CreateUserRequestDto createUserRequestDto);

    GetUserResponseDto findUserByEmail(GetByEmailRequestDto getByEmailRequest);

    GetUserProfileResponseDto getUserProfile(GetByIdRequestDto getByIdRequestDto);

    GetUserProfileDetailResponseDto getUserProfileDetail(GetByIdRequestDto getByIdRequestDto);

    LocalDate getUserBirthday(Long userId);

    void updateUserStatus(Long userId);

    List<UserInfoResponseDto> searchUsers(String searchWord);
}
