package com.example.demo.dto;

import java.util.List;

public record RegisterDto(
        String userType,
        String username,
        String password,
        String email,
        String studentEducation,
        String address,
        String education,
        List<String> skill
) {}
