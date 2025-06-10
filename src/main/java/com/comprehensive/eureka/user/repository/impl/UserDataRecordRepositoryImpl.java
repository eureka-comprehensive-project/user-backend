package com.comprehensive.eureka.user.repository.impl;

import com.comprehensive.eureka.user.dto.response.UserDataRecordResponseDto;
import com.comprehensive.eureka.user.repository.custom.UserDataRecordRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDataRecordRepositoryImpl implements UserDataRecordRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<UserDataRecordResponseDto> findRecentDataUsageByUserId(Long userId) {

    }
}

