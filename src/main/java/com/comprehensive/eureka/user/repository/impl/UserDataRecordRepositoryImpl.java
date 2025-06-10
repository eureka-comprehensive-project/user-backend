package com.comprehensive.eureka.user.repository.impl;

import com.comprehensive.eureka.user.dto.response.UserDataRecordResponseDto;
import com.comprehensive.eureka.user.repository.custom.UserDataRecordRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import static com.comprehensive.eureka.user.entity.QUserDataRecord.userDataRecord;

@Repository
@RequiredArgsConstructor
public class UserDataRecordRepositoryImpl implements UserDataRecordRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<UserDataRecordResponseDto> findUserUsage(Long userId, int monthCount) {
        return queryFactory
                .select(Projections.constructor(
                        UserDataRecordResponseDto.class,
                        userDataRecord.user.id,
                        userDataRecord.dataUsage,
                        userDataRecord.dataUsageUnit,
                        userDataRecord.messageUsage,
                        userDataRecord.callUsage,
                        userDataRecord.yearMonth
                ))
                .from(userDataRecord)
                .where(userDataRecord.user.id.eq(userId))
                .orderBy(userDataRecord.yearMonth.desc())
                .limit(monthCount)
                .fetch();
    }
}

