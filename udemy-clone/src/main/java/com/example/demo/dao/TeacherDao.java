package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Teacher;

import java.util.Optional;

public interface TeacherDao extends JpaRepository<Teacher, Long>{
    Optional<Teacher> findByUsername(String teacherName);
}
