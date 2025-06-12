package com.comprehensive.eureka.user.service.impl;

import com.comprehensive.eureka.user.dto.request.CreateUserPlanRecordRequestDto;
import com.comprehensive.eureka.user.dto.response.UserActivePlanBenefitResponseDto;
import com.comprehensive.eureka.user.entity.User;
import com.comprehensive.eureka.user.entity.UserPlanRecord;
import com.comprehensive.eureka.user.exception.UserNotFoundException;
import com.comprehensive.eureka.user.repository.UserPlanRecordRepository;
import com.comprehensive.eureka.user.repository.UserRepository;
import com.comprehensive.eureka.user.service.UserPlanRecordService;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserPlanRecordServiceImpl implements UserPlanRecordService {
    private final UserPlanRecordRepository userPlanRecordRepository;
    private final UserRepository userRepository;

    @Override
    public List<UserActivePlanBenefitResponseDto> getActivePlanBenefits(List<Long> userIds) {
        log.info("사용자 활성 요금제 혜택 조회 요청 - userIds: {}", userIds);
        List<Tuple> results = userPlanRecordRepository.findActivePlanBenefitsByUserIds(userIds);

        Map<Long, Long> benefitMap = results.stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(0, Long.class),
                        tuple -> tuple.get(1, Long.class)
                ));
        log.info("조회된 혜택 매핑: {}", benefitMap);

        // 요청받은 모든 userId 기준으로 응답 생성 (없으면 planBenefitId = null)
        return userIds.stream()
                .map(id -> new UserActivePlanBenefitResponseDto(id, benefitMap.get(id)))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void createUserPlanRecord(CreateUserPlanRecordRequestDto createUserPlanRecordRequestDto) {
        Long userId = createUserPlanRecordRequestDto.getUserId();
        Long planBenefitId = createUserPlanRecordRequestDto.getPlanBenefitId();
        LocalDate now = LocalDate.now();

        log.info("요금제 등록 요청 - userId: {}, planBenefitId: {}", userId, planBenefitId);

        // 가입한 사용자인지 확인
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        // 기존에 가입한 요금제가 있는지 확인
        Optional<UserPlanRecord> activeRecordOpt = userPlanRecordRepository.findByUserIdAndIsActiveTrue(userId);

        // 기존 기록 만료 처리
        if (activeRecordOpt.isPresent()) {
            log.info("기존 활성화된 요금제 기록 존재 - userId: {}", userId);
            UserPlanRecord record = activeRecordOpt.get();
            record.setExpirationDate(now);
            record.setIsActive(false);
            userPlanRecordRepository.save(record);
            log.info("기존 요금제 기록 만료 처리 완료 - recordId: {}", record.getId());
        } else {
            log.info("활성화된 기존 요금제 없음 - userId: {}", userId);
        }

        // 새로 등록
        UserPlanRecord newRecord = UserPlanRecord.builder()
                .user(user)
                .planBenefitId(planBenefitId)
                .contractDate(now)
                .expirationDate(now.plusYears(1))
                .isActive(true)
                .build();

        userPlanRecordRepository.save(newRecord);
        log.info("새로운 요금제 기록 등록 완료 - userId: {}, planBenefitId: {}", userId, planBenefitId);
    }
}
