package com.comprehensive.eureka.user.service;

import com.comprehensive.eureka.user.dto.request.CreateUserRequestDto;
import com.comprehensive.eureka.user.dto.request.GetByEmailRequestDto;
import com.comprehensive.eureka.user.dto.request.GetByIdRequestDto;
import com.comprehensive.eureka.user.dto.response.CreateUserResponseDto;
import com.comprehensive.eureka.user.dto.response.GetUserProfileDetailResponseDto;
import com.comprehensive.eureka.user.dto.response.GetUserProfileResponseDto;
import com.comprehensive.eureka.user.dto.response.GetUserResponseDto;

public interface UserService {
    CreateUserResponseDto createUser(CreateUserRequestDto createUserRequestDto);

    GetUserResponseDto findUserByEmail(GetByEmailRequestDto getByEmailRequest);

    GetUserProfileResponseDto getUserProfile(GetByIdRequestDto getByIdRequestDto);

    GetUserProfileDetailResponseDto getUserProfileDetail(GetByIdRequestDto getByIdRequestDto);
}
