package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Base64;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CourseDto {
    private long courseId;
    private String title;
    private String description;
    private double fees;
    private String categoryName;
    private String teacherName;
    private String imageBase64;

    public CourseDto(long courseId,
                     String title,
                     String description,
                     double fees,
                     String categoryName,
                     String teacherName,
                     byte[] imageBase64) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.fees = fees;
        this.categoryName = categoryName;
        this.teacherName = teacherName;
        this.imageBase64 = imageBase64 != null ? Base64.getEncoder()
                                                 .encodeToString(imageBase64) : null;
    }
}
