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
        log.error(e.getMessage());
        return BaseResponseDto.fail(ErrorCode.USER_NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public BaseResponseDto<ErrorResponseDto> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        log.error(e.getMessage());
        return BaseResponseDto.fail(ErrorCode.USER_EMAIL_ALREADY_EXISTS);
    }

    @ExceptionHandler(InternalServerException.class)
    public BaseResponseDto<ErrorResponseDto> handleInternalServerException(InternalServerException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error("InternalServerException 발생 - code: {}, message: {}", errorCode.getCode(), errorCode.getMessage());
        return BaseResponseDto.fail(errorCode);
    }

    @ExceptionHandler(Exception.class)
    public BaseResponseDto<ErrorResponseDto> handleUnexpectedException(Exception e) {
        log.error("[GlobalExceptionHandler] 사용자 서버 처리 중 알 수 없는 오류 발생");
        return BaseResponseDto.fail(ErrorCode.USER_SERVER_UNKNOWN_ERROR);
    }
}
