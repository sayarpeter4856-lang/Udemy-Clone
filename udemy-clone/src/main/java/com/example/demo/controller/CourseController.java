package com.example.demo.controller;

import com.example.demo.dto.CourseDto;
import com.example.demo.dto.EnrolledDto;
import com.example.demo.entity.CourseLessons;
import com.example.demo.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public List<CourseDto> findAllCourses() {
        return courseService.findAllCourses();
    }

    @GetMapping("/{id}/lessons")
    public List<CourseLessons> fetchAllCourseLessons(@PathVariable("id") Long courseId) {
        return  courseService.findAllCourseLessons(courseId);
    }

    @PostMapping
    public ResponseEntity<String> createCourse(
            @RequestParam("title") String title,
            @RequestParam("fees") double fees,
            @RequestParam("description") String description,
            @RequestParam("category_name") String categoryName,
            @RequestParam(value = "image", required = false) MultipartFile image,
            Principal principal)
    throws IOException {
        String teacherName = principal.getName();
        courseService.createCourse(title, fees, description, categoryName, image, teacherName);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Course created successfully");
    }

    @GetMapping("/teacher")
    public List<CourseDto> findAllCoursesByTeacher(Principal principal) {
        return courseService.findAllCoursesByTeacher(principal.getName());
    }

    @PostMapping("/enroll")
    public ResponseEntity<String> enrolledCourse(@RequestBody EnrolledDto enrolledDto,
                                                 Principal principal) {
        String returnString = courseService.enrolledCourse(
                enrolledDto.courseIds(), principal.getName());
        return new ResponseEntity<>(returnString, HttpStatus.CREATED);
    }

    @GetMapping("/enroll-courses")
    public List<CourseDto> fetchAllEnrolledCourse(Principal principal) {
        return courseService.getAllEnrolledCourses(principal.getName());
    }

    @PostMapping("/{id}/lessons")
    public ResponseEntity<String> addLessons(@RequestBody CourseLessons courseLessons,
                                             @PathVariable("id") long courseId) {
        String returnString = courseService.addLessons(courseLessons, courseId);
        return ResponseEntity.ok().body(returnString);
    }

}
