package com.comprehensive.eureka.user.scheduler;

import com.comprehensive.eureka.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserScheduler {

    private final UserService userService;

    @Scheduled(cron = "10 0 0 * * *")
    public void unbanUserScheduler() {
        log.info("사용자 차단 해제 스케줄러 시작...");
        userService.unbanExpiredUsers();
        log.info("사용자 차단 해제 스케줄러 완료.");
    }
}
