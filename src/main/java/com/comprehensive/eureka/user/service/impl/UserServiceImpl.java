package com.comprehensive.eureka.user.service.impl;

import com.comprehensive.eureka.user.dto.request.CreateUserRequestDto;
import com.comprehensive.eureka.user.dto.response.CreateUserResponseDto;
import com.comprehensive.eureka.user.dto.response.GetUserResponseDto;
import com.comprehensive.eureka.user.entity.User;
import com.comprehensive.eureka.user.repository.UserRepository;
import com.comprehensive.eureka.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public CreateUserResponseDto createUser(CreateUserRequestDto createUserRequestDto) {
        // TODO 이메일 중복체크 후 예외처리
        User user = userRepository.save(CreateUserRequestDto.toEntity(createUserRequestDto));
        return CreateUserResponseDto.builder().id(user.getId()).build();
    }

    @Override
    public GetUserResponseDto findUserByEmail(String email) {
        return GetUserResponseDto.from(
                userRepository.findByEmail(email).orElseThrow(
                        // TODO Exception 다른걸로 변경 필요
                        () -> new RuntimeException("user 를 찾는중에 오류가 발생하였습니다.")
                )
        );
    }
}
