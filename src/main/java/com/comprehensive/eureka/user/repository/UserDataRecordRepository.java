package com.comprehensive.eureka.user.repository;

import com.comprehensive.eureka.user.entity.UserDataRecord;
import com.comprehensive.eureka.user.repository.custom.UserDataRecordRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRecordRepository extends JpaRepository<UserDataRecord, Long>, UserDataRecordRepositoryCustom {
    boolean existsByUserIdAndYearMonth(Long userId, String yearMonth);
}
