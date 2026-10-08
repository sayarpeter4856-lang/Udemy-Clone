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
@DiscriminatorValue("STUDENT")
public class Student extends User {

	private String address;

	public Student(String username, String password, String email, String address, StudentEducation studentEducation) {
		super(username, password, email);
		this.address = address;
		this.studentEducation = studentEducation;
	}

	@Enumerated(EnumType.STRING)
	private StudentEducation studentEducation;
	@OneToMany(mappedBy = "student")
	private List<StudentEnrollCourse> enrollCourses = new ArrayList<>();
	public void  addEnrollCourse(StudentEnrollCourse studentEnrollCourse) {
		studentEnrollCourse.setStudent(this);
		enrollCourses.add(studentEnrollCourse);

	}

}
