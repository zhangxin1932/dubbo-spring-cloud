package com.zy.aspect;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleValidException(MethodArgumentNotValidException e){
        e.printStackTrace();
        if (Objects.nonNull(e.getBindingResult().getFieldError())) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getBindingResult().getFieldError().getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
    }

    @ExceptionHandler(Throwable.class)
    public Object handleException(Throwable e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
