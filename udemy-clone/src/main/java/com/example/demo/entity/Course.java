package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Course extends IdClass{
    private String title;
    private double fees;
    @Column(columnDefinition = "text")
    private String description;
    private int studentCount;
    @Lob
    private byte[] image;

    @ManyToOne
    private Category category;
    @ManyToOne
    private Teacher teacher;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<CourseLessons> courseLessonsList = new ArrayList<>();

    public void addCourseLesson(CourseLessons courseLessons) {
        courseLessonsList.add(courseLessons);
    }

    @OneToMany(mappedBy = "course")
    private List<StudentEnrollCourse> studentEnrollCourseList = new ArrayList<>();
    public void addStudentEnrollCourse(StudentEnrollCourse studentEnrollCourse) {
        studentEnrollCourse.setCourse(this);
        studentEnrollCourseList.add(studentEnrollCourse);
    }
}
