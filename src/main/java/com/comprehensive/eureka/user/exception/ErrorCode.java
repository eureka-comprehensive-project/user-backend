package com.comprehensive.eureka.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(50000, "USER_NOT_FOUND", "해당 사용자를 찾을 수 없습니다."),
    USER_EMAIL_ALREADY_EXISTS(50001, "USER_EMAIL_ALREADY_EXISTS", "중복된 email 입니다."),
    USER_CREATE_FAIL(50002, "USER_CREATE_FAIL", "사용자 등록 중 오류가 발생했습니다."),
    USER_PLAN_RECORD_CREATE_FAIL(50003, "USER_PLAN_RECORD_CREATE_FAIL", "사용자 요금제 등록 중 오류가 발생했습니다."),
    USER_DATA_RECORD_DUPLICATE(50004, "USER_DATA_RECORD_DUPLICATE", "이미 해당 월에 사용자 기록이 존재합니다."),
    INVALID_SEARCH_REQUEST(50005, "INVALID_SEARCH_REQUEST", "검색어를 입력해 주세요.");

    private final int code;
    private final String name;
    private final String message;
}
