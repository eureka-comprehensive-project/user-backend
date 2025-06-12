package com.comprehensive.eureka.user.repository;

import com.comprehensive.eureka.user.entity.UserPlanRecord;
import com.comprehensive.eureka.user.repository.custom.UserPlanRecordRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserPlanRecordRepository extends JpaRepository<UserPlanRecord, Long>, UserPlanRecordRepositoryCustom {
    Optional<UserPlanRecord> findByUserIdAndIsActiveTrue(Long userId);
}
