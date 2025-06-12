package com.comprehensive.eureka.user.service.impl;

import com.comprehensive.eureka.user.dto.response.UserActivePlanBenefitResponseDto;
import com.comprehensive.eureka.user.repository.UserPlanRecordRepository;
import com.comprehensive.eureka.user.service.UserPlanRecordService;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserPlanRecordServiceImpl implements UserPlanRecordService {
    private final UserPlanRecordRepository userPlanRecordRepository;
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
}
