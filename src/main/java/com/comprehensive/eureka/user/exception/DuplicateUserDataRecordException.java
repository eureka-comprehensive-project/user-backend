package com.comprehensive.eureka.user.exception;

public class DuplicateUserDataRecordException extends RuntimeException{
    public DuplicateUserDataRecordException() {
        super("이미 해당 월에 사용자 기록이 존재합니다.");
    }
}
