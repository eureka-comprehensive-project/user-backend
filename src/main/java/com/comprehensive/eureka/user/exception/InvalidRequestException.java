package com.comprehensive.eureka.user.exception;

public class InvalidRequestException extends RuntimeException{
    public InvalidRequestException() { super("검색어를 입력해 주세요."); }
}
