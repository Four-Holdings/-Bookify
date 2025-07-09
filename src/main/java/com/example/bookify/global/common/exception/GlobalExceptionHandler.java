package com.example.bookify.global.common.exception;

import com.example.bookify.global.common.apiresponse.ErrorResponseDto;
import com.example.bookify.global.common.exception.exceptionclass.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> customException(CustomException exception, HttpServletRequest request) {
        return customErrorResponse(
                exception.getExceptionCode().getHttpStatus(),
                exception.getMessage(),
                request.getRequestURI());
    }

    // Response객체 생성 메서드
    private ResponseEntity<ErrorResponseDto> customErrorResponse(HttpStatus status, String message, String path) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                status.value(),
                status.getReasonPhrase(),
                message,
                path
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }

}
