package com.comprehensive.eureka.user.exception;

import com.comprehensive.eureka.user.dto.base.BaseResponseDto;
import com.comprehensive.eureka.user.dto.response.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public BaseResponseDto<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException e) {
        log.error(e.getMessage(), e);
        return BaseResponseDto.fail(ErrorCode.USER_NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public BaseResponseDto<ErrorResponseDto> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        log.error(e.getMessage(), e);
        return BaseResponseDto.fail(ErrorCode.EMAIL_ALREADY_EXISTS);
    }
}
