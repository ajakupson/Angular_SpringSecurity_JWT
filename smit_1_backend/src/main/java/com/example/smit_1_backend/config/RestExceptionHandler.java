package com.example.smit_1_backend.config;

import com.example.smit_1_backend.dtos.SmitResponse;
import com.example.smit_1_backend.exceptions.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = { AppException.class })
    @ResponseBody
    public SmitResponse handleException(AppException ex) {
        return new SmitResponse(
                false,
                ex.getStatus(),
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(value = { Exception.class })
    @ResponseBody
    public SmitResponse handleException(Exception ex) {
        return new SmitResponse(
                false,
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                null
        );
    }
}
