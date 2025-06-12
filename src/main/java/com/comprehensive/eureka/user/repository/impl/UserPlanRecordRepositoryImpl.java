package com.comprehensive.eureka.user.repository.impl;

import com.comprehensive.eureka.user.repository.custom.UserPlanRecordRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.comprehensive.eureka.user.entity.QUserPlanRecord.userPlanRecord;

@Repository
@RequiredArgsConstructor
public class UserPlanRecordRepositoryImpl implements UserPlanRecordRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public List<Tuple> findActivePlanBenefitsByUserIds(List<Long> userIds) {
        return queryFactory
                .select(userPlanRecord.user.id, userPlanRecord.planBenefitId)
                .from(userPlanRecord)
                .where(
                        userPlanRecord.user.id.in(userIds),
                        userPlanRecord.isActive.isTrue()
                )
                .fetch();
    }
}
