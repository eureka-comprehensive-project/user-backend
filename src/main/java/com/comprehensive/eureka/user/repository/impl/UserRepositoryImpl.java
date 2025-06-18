package com.comprehensive.eureka.user.repository.impl;

import com.comprehensive.eureka.user.dto.response.UserInfoResponseDto;
import com.comprehensive.eureka.user.entity.User;
import com.comprehensive.eureka.user.entity.enums.Status;
import com.comprehensive.eureka.user.repository.custom.UserRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.comprehensive.eureka.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<User> findUserByEmailAndStatus(String email) {
        return Optional.ofNullable(
                queryFactory.selectFrom(user)
                        .where(
                                user.email.eq(email),
                                user.status.eq(Status.ACTIVE)
                        )
                        .fetchOne()
        );
    }

    @Override
    public List<UserInfoResponseDto> searchUsersBySearchWord(String searchWord) {
        return queryFactory
                .select(Projections.constructor(
                        UserInfoResponseDto.class,
                        user.id,
                        user.email,
                        user.name,
                        user.phone,
                        user.birthday,
                        user.createdAt,
                        user.status,
                        user.unbanTime
                ))
                .from(user)
                .where(
                        user.name.containsIgnoreCase(searchWord)
                                .or(user.email.containsIgnoreCase(searchWord))
                )
                .fetch();
    }

    @Override
    public long bulkUnbanUsers(LocalDateTime now) {
        return queryFactory
                .update(user)
                .set(user.status, Status.ACTIVE)
                .set(user.unbanTime, (LocalDateTime) null)
                .where(
                        user.status.eq(Status.INACTIVE),
                        user.unbanTime.lt(now)
                )
                .execute();
    }
}
