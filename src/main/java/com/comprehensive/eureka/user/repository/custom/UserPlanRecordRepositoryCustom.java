package com.comprehensive.eureka.user.repository.custom;

import com.querydsl.core.Tuple;

import java.util.List;

public interface UserPlanRecordRepositoryCustom {
    List<Tuple> findActivePlanBenefitsByUserIds(List<Long> userIds);
}
