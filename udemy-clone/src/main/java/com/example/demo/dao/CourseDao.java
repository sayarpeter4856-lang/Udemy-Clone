package com.example.demo.dao;

import com.example.demo.dto.CourseDto;
import com.example.demo.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseDao extends JpaRepository<Course, Long> {

    @Query(""" 
    select new com.example.demo.dto.CourseDto(
    c.id,
    c.title,
    c.description,
    c.fees,
    category.categoryName,
    teacher.username,
    c.image
    )from Course c join c.category category join c.teacher teacher 
        """)
    List<CourseDto> findAllCourses();

    @Query(""" 
    select new com.example.demo.dto.CourseDto(
    c.id,
    c.title,
    c.description,
    c.fees,
    category.categoryName,
    teacher.username,
    c.image
    )from Course c join c.category category join c.teacher teacher where teacher.username = :username
        """)
    List<CourseDto> findAllCoursesByTeacherName(@Param("username") String username);

}
