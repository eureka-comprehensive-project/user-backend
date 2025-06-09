package com.comprehensive.eureka.user.service.impl;

import com.comprehensive.eureka.user.dto.request.CreateUserRequestDto;
import com.comprehensive.eureka.user.dto.request.GetByEmailRequestDto;
import com.comprehensive.eureka.user.dto.request.GetByIdRequestDto;
import com.comprehensive.eureka.user.dto.response.CreateUserResponseDto;
import com.comprehensive.eureka.user.dto.response.GetUserProfileDetailResponseDto;
import com.comprehensive.eureka.user.dto.response.GetUserProfileResponseDto;
import com.comprehensive.eureka.user.dto.response.GetUserResponseDto;
import com.comprehensive.eureka.user.entity.User;
import com.comprehensive.eureka.user.exception.UserNotFoundException;
import com.comprehensive.eureka.user.repository.UserRepository;
import com.comprehensive.eureka.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public CreateUserResponseDto createUser(CreateUserRequestDto createUserRequestDto) {
        Optional<User> optionalUser = userRepository.findUserByEmailAndStatus(createUserRequestDto.getEmail());
        if (optionalUser.isPresent()) {
            throw new RuntimeException("중복된 email 입니다.");
        }

        User user = userRepository.save(CreateUserRequestDto.toEntity(createUserRequestDto));
        return CreateUserResponseDto.builder()
                .id(user.getId())
                .build();
    }

    @Override
    public GetUserResponseDto findUserByEmail(GetByEmailRequestDto getByEmailRequest) {
        return GetUserResponseDto.from(
                userRepository.findUserByEmailAndStatus(getByEmailRequest.getEmail()).orElseThrow(
                        // TODO Exception 다른걸로 변경 필요
                        () -> new RuntimeException("user 를 찾는중에 오류가 발생하였습니다.")
                )
        );
    }

    @Override
    public GetUserProfileResponseDto getUserProfile(GetByIdRequestDto getByIdRequestDto){
        return GetUserProfileResponseDto.from(
                userRepository.findById(getByIdRequestDto.getId()).orElseThrow(
                        UserNotFoundException::new)
        );
    }

    @Override
    public GetUserProfileDetailResponseDto getUserProfileDetail(GetByIdRequestDto getByIdRequestDto){
        return GetUserProfileDetailResponseDto.from(
                userRepository.findById(getByIdRequestDto.getId()).orElseThrow(
                        UserNotFoundException::new)
        );
    }

    @Override
    public LocalDate getUserBirthday(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return user.getBirthday();
    }
}
