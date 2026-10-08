package com.example.demo.dao;

import com.example.demo.dto.CourseDto;
import com.example.demo.entity.StudentEnrollCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentEnrollCourseDao extends
        JpaRepository<StudentEnrollCourse, Long> {
    //Already Enrolled Course
    @Query("""
        SELECT se FROM StudentEnrollCourse se 
                WHERE se.student.username = :username 
                AND se.course.id = :courseId
                """)
    Optional<StudentEnrollCourse>
        testAlreadyEnrolledCourse(@Param("username") String username,
                                  @Param("courseId") Long courseId);

    @Query(""" 
    select new com.example.demo.dto.CourseDto(
    c.id,
    c.title,
    c.description,
    c.fees,
    category.categoryName,
    t.username,
    c.image
    )from StudentEnrollCourse se 
        join se.student s 
        join se.course c
        join c.category category
        join c.teacher t 
        where s.username = :username
        """)
    List<CourseDto> findCourseByLoginUser(@Param("username") String username);

}
