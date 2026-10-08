package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Student;

import java.util.Optional;

public interface StudentDao extends JpaRepository<Student, Long>{
    Optional<Student> findByUsername(String username);
}
