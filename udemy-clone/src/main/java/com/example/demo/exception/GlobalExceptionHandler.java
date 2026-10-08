package com.example.demo.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {TeacherNotFoundException.class, ConstraintViolationException.class, StudentNotFoundException.class,
            AlreadyEnrolledException.class})
    public ResponseEntity<Object> globalExceptionMethod(Exception exception, WebRequest request)
            throws Exception {
        Map<String, String> errorMap = Map.of("ErrorMessage", exception.getMessage(),
                "ErrorTime", LocalDateTime.now().toString(),
                "ErrorStatus", HttpStatus.BAD_REQUEST.toString(),
                "ErrorDetails", request.toString());
        return handleExceptionInternal(
                exception,
                errorMap,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }

}
