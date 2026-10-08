package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category extends IdClass{
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<Course> courses =
            new ArrayList<>();

    public void addCourse(Course course) {
        courses.add(course);
        course.setCategory(this);
    }

}
