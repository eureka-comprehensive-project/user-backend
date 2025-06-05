package com.comprehensive.eureka.user.repository.impl;

import com.comprehensive.eureka.user.entity.User;
import com.comprehensive.eureka.user.entity.enums.Status;
import com.comprehensive.eureka.user.repository.custom.UserRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
