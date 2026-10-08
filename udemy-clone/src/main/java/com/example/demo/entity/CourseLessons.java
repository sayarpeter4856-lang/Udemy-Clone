package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;

@Embeddable
public record CourseLessons(
        @JsonProperty("lesson_name")
        String lessonName,

        @JsonProperty("lesson_linked")
        String lessonLinked
) {
}
