package com.comprehensive.eureka.user.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super("중복된 email 입니다.");
    }
}
