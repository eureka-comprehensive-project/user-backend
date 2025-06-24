package com.comprehensive.eureka.user.service.impl;

import com.comprehensive.eureka.user.dto.request.CreateUserRequestDto;
import com.comprehensive.eureka.user.dto.request.GetByEmailRequestDto;
import com.comprehensive.eureka.user.dto.request.GetByIdRequestDto;
import com.comprehensive.eureka.user.dto.request.UpdateUserStatusRequestDto;
import com.comprehensive.eureka.user.dto.response.*;
import com.comprehensive.eureka.user.entity.User;
import com.comprehensive.eureka.user.entity.enums.Status;
import com.comprehensive.eureka.user.exception.EmailAlreadyExistsException;
import com.comprehensive.eureka.user.exception.ErrorCode;
import com.comprehensive.eureka.user.exception.InternalServerException;
import com.comprehensive.eureka.user.exception.UserNotFoundException;
import com.comprehensive.eureka.user.repository.UserRepository;
import com.comprehensive.eureka.user.service.RedisService;
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
    private final RedisService redisService;

    @Override
    public CreateUserResponseDto createUser(CreateUserRequestDto createUserRequestDto) {
        log.info("[createUser] 사용자 등록 요청");

        Optional<User> optionalUser = userRepository.findByEmail(createUserRequestDto.getEmail());
        if (optionalUser.isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        try {
            User user = userRepository.save(CreateUserRequestDto.toEntity(createUserRequestDto));
            return CreateUserResponseDto.builder()
                    .id(user.getId())
                    .build();
        } catch (Exception e) {
            throw new InternalServerException(ErrorCode.USER_CREATE_FAIL);
        }

    }

    @Override
    public GetUserResponseDto findUserByEmail(GetByEmailRequestDto getByEmailRequest) {
        log.info("[findUserByEmail] 사용자 조회 요청");

        return GetUserResponseDto.from(
                userRepository.findUserByEmailAndStatus(getByEmailRequest.getEmail()).orElseThrow(
                        UserNotFoundException::new)
        );
    }

    @Override
    public GetUserProfileResponseDto getUserProfile(GetByIdRequestDto getByIdRequestDto) {
        log.info("[getUserProfile] 프로필 조회 요청");
        return GetUserProfileResponseDto.from(
                userRepository.findById(getByIdRequestDto.getId()).orElseThrow(
                        UserNotFoundException::new)
        );
    }

    @Override
    public GetUserProfileDetailResponseDto getUserProfileDetail(GetByIdRequestDto getByIdRequestDto) {
        log.info("[getUserProfileDetail] 상세 프로필 조회 요청");
        return GetUserProfileDetailResponseDto.from(
                userRepository.findById(getByIdRequestDto.getId()).orElseThrow(
                        UserNotFoundException::new)
        );
    }

    @Override
    public LocalDate getUserBirthday(Long userId) {
        log.info("[getUserBirthday] 생년월일 조회 요청");
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return user.getBirthday();
    }

    @Override
    public void updateUserStatus(Long userId) {
        log.info("[updateUserStatus] 사용자 상태 변경 요청");
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Status oldStatus = user.getStatus();
        user.changeStatus();
        // TODO
        /**
         * 차단인 경우 > 레디스에 해당 사용자 값 등록
         * 해제인 경우 > 레디스에 해당 사용자 값 삭제
         */
        if (!oldStatus.equals(Status.INACTIVE)) {
            redisService.save("blacklist:user:" + user.getId(), "blocked", 3600);
            log.info("save >>> blacklist:user:" + user.getId());
        } else {
            redisService.delete("blacklist:user:" + user.getId());
            log.info("delete >>> blacklist:user:" + user.getId());
        }

        log.info("[updateUserStatus] 상태 변경 완료 - userId: {}, {} → {}", userId, oldStatus, user.getStatus());
    }

    @Override
    public List<UserInfoResponseDto> searchUsers(String searchWord) {
        log.info("[searchUsers] 사용자 검색 요청");
        return userRepository.searchUsersBySearchWord(searchWord);
    }

    @Override
    public void updateUserStatusAndTime(UpdateUserStatusRequestDto updateUserStatusRequestDto) {
        log.info("[updateUserStatusAndTime] 상태/차단해제시간 변경 요청");
        User user = userRepository.findById(updateUserStatusRequestDto.getUserId())
                .orElseThrow(UserNotFoundException::new);

        Status oldStatus = user.getStatus();
        LocalDateTime oldUnbanTime = user.getUnbanTime();

        user.changeStatusAndTime(updateUserStatusRequestDto.getStatus(), updateUserStatusRequestDto.getUnbanTime());
        log.info("[updateUserStatusAndTime] 사용자 상태 변경 - userId: {}, status: {} → {}, unbanTime: {} → {}",
                updateUserStatusRequestDto.getUserId(),
                oldStatus, updateUserStatusRequestDto.getStatus(),
                oldUnbanTime, updateUserStatusRequestDto.getUnbanTime());
    }

    @Override
    public void updateUserStatusActive(GetByEmailRequestDto getByEmailRequestDto) {
        log.info("[updateUserStatusActive] 이메일 인증 롼료 사용자 활성화 요청");
        User user = userRepository.findByEmail(getByEmailRequestDto.getEmail())
                .orElseThrow(UserNotFoundException::new);

        user.changeStatusAndTime(Status.ACTIVE, null);

        log.info("[updateUserStatusActive] 사용자 활성화 - email: {}", getByEmailRequestDto.getEmail());
    }

    @Override
    public Boolean emailExists(GetByEmailRequestDto getByEmailRequestDto) {
        String email = getByEmailRequestDto.getEmail();
        log.info("[emailExists] 이메일 중복 확인 요청 - email: {}", email);
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public void unbanExpiredUsers() {
        long updateCount = userRepository.bulkUnbanUsers(LocalDateTime.now());
        log.info("[unbanExpireUsers] 총 {} 명의 사용자 차단 해제 완료", updateCount);
    }

    @Override
    public GetUserResponseDto findOAuthUserByEmail(GetByEmailRequestDto getByEmailRequest) {
        log.info("[findOAuthUserByEmail] 사용자 조회 요청");

        return GetUserResponseDto.from(
                userRepository.findOAuthUserByEmail(getByEmailRequest.getEmail()).orElseThrow(
                        UserNotFoundException::new)
        );
    }
}
