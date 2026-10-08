package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlreadyEnrolledException extends ResponseStatusException {
    public AlreadyEnrolledException(String studentName, Long courseId) {
        super(HttpStatus.BAD_REQUEST,
                "Student %s is already enrolled in course %s"
                        .formatted(studentName, courseId));
    }
}
