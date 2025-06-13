package com.comprehensive.eureka.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(50000, "USER_NOT_FOUND", "해당 사용자를 찾을 수 없습니다."),
    USER_EMAIL_ALREADY_EXISTS(50001, "USER_EMAIL_ALREADY_EXISTS", "중복된 email 입니다."),
    USER_CREATE_FAIL(50002, "USER_CREATE_FAIL", "사용자 등록 중 오류가 발생했습니다."),
    USER_PLAN_RECORD_CREATE_FAIL(50004, "USER_PLAN_RECORD_CREATE_FAIL", "사용자 요금제 등록 중 오류가 발생했습니다."),
    USER_SERVER_UNKNOWN_ERROR(50005, "USER_SERVER_UNKNOWN_ERROR", "사용자 서버 처리 중 예기치 못한 오류가 발생했습니다.");

    private final int code;
    private final String name;
    private final String message;
}
