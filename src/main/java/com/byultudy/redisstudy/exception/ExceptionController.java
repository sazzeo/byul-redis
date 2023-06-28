package com.byultudy.redisstudy.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler({SoldOutException.class , BadRequestException.class})
    public String returnMessage(RuntimeException e) {
        return e.getMessage();
    }
}
