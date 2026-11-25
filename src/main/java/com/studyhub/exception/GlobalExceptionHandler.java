package com.studyhub.exception;

import com.studyhub.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 1. 处理业务异常
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result<Void>> handleBusinessException(
            BusinessException e) {

        return ResponseEntity
                .status(e.getCode())
                .body(new Result<>(
                        e.getCode(),
                        e.getMessage(),
                        null));
    }

    // 2. 处理 DTO 参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<Void>> handleValidationException(
            MethodArgumentNotValidException e) {

        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("请求参数不合法");

        return ResponseEntity
                .badRequest()
                .body(new Result<>(400, message, null));
    }
}