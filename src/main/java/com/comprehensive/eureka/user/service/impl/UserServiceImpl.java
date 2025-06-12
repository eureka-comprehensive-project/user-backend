package com.comprehensive.eureka.user.service.impl;

import com.comprehensive.eureka.user.dto.request.CreateUserRequestDto;
import com.comprehensive.eureka.user.dto.request.GetByEmailRequestDto;
import com.comprehensive.eureka.user.dto.request.GetByIdRequestDto;
import com.comprehensive.eureka.user.dto.request.UpdateUserStatusRequestDto;
import com.comprehensive.eureka.user.dto.response.*;
import com.comprehensive.eureka.user.entity.User;
import com.comprehensive.eureka.user.entity.enums.Status;
import com.comprehensive.eureka.user.exception.EmailAlreadyExistsException;
import com.comprehensive.eureka.user.exception.UserNotFoundException;
import com.comprehensive.eureka.user.repository.UserRepository;
import com.comprehensive.eureka.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public CreateUserResponseDto createUser(CreateUserRequestDto createUserRequestDto) {
        Optional<User> optionalUser = userRepository.findByEmail(createUserRequestDto.getEmail());
        if (optionalUser.isPresent()) {
            throw new EmailAlreadyExistsException();
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
                        UserNotFoundException::new)
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

    @Override
    public void updateUserStatus(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        user.changeStatus();
    }

    @Override
    public List<UserInfoResponseDto> searchUsers(String searchWord) {
        return userRepository.searchUsersBySearchWord(searchWord);
    }

    @Override
    public void updateUserStatusAndTime(UpdateUserStatusRequestDto updateUserStatusRequestDto) {
        User user = userRepository.findById(updateUserStatusRequestDto.getUserId())
                .orElseThrow(UserNotFoundException::new);

        Status oldStatus = user.getStatus();
        LocalDateTime oldUnbanTime = user.getUnbanTime();

        user.changeStatusAndTime(updateUserStatusRequestDto.getStatus(), updateUserStatusRequestDto.getUnbanTime());
        log.info("사용자 상태 변경 - userId: {}, status: {} → {}, unbanTime: {} → {}",
                updateUserStatusRequestDto.getUserId(),
                oldStatus, updateUserStatusRequestDto.getStatus(),
                oldUnbanTime, updateUserStatusRequestDto.getUnbanTime());
    }

    @Override
    public void updateUserStatusActive(GetByEmailRequestDto getByEmailRequestDto) {
        User user = userRepository.findByEmail(getByEmailRequestDto.getEmail())
                .orElseThrow(UserNotFoundException::new);

        user.changeStatusAndTime(Status.ACTIVE, null);

        log.info("사용자 활성화 - email: {}, status: INACTIVE → ACTIVE", getByEmailRequestDto.getEmail());
    }

    @Override
    public Boolean emailExists(GetByEmailRequestDto getByEmailRequestDto) {
        String email = getByEmailRequestDto.getEmail();
        log.info("이메일 중복 확인 요청 - email: {}", email);
        return userRepository.existsByEmail(email);
    }
}
