package com.example.demo;

import com.example.demo.dao.*;
import com.example.demo.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@SpringBootApplication
@RequiredArgsConstructor
public class UdemyCloneApplication {

	private final TeacherDao teacherDao;
	private final StudentDao studentDao;
	private final RoldeDao roldeDao;
	private final SiteOwnerDao siteOwnerDao;
	private final PasswordEncoder passwordEncoder;
	private final StudentEnrollCourseDao studentEnrollCourseDao;

	private Role getRoleByName(String roleName) {
		return  roldeDao.findByRoleName(roleName)
				.orElseGet(() -> {
					com.example.demo.entity.Role role = new Role();
					role.setRoleName(roleName);
					return role;
				});
	}

	@Bean
	@Profile("dev")
	public ApplicationRunner runner() {
		return args -> {
			studentEnrollCourseDao.findCourseByLoginUser("Thomas")
					.forEach(System.out::println);
//			Role teacherRole = getRoleByName("ROLE_TEACHER");
//			Teacher teacher = new Teacher(
//					"John",
//					passwordEncoder.encode("123456"),
//					"john@gmail.com",
//					BigDecimal.valueOf(0),
//					"Master of Science in Computer Science");
//			teacher.addSkill("Java");
//			teacher.addSkill("JavaScript");
//			teacher.addSkill("CS");
//			teacher.addRole(roldeDao.save(teacherRole));
//			teacherDao.save(teacher);
//
//			Role studentRole = getRoleByName("ROLE_STUDENT");
//			Student student = new Student(
//					"Mary",
//					passwordEncoder.encode("12345"),
//					"mary@gmail.com",
//					"No.11 Strand RD",
//					StudentEducation.UNDER_GRADUTE);
//			student.addRole(roldeDao.save(studentRole));
//			studentDao.save(student);
//
//			Role siteOwnerRole = getRoleByName("ROLE_SITE_OWNER");
//			SiteOwner siteOwner = new SiteOwner(
//					"William",
//					passwordEncoder.encode("123456"),
//					"william@gmail.com");
//			siteOwner.setPlatformShare(BigDecimal.valueOf(0));
//			siteOwner.addRole(roldeDao.save(siteOwnerRole));
//			siteOwnerDao.save(siteOwner);

		};
	}


	public static void main(String[] args) {
		SpringApplication.run(UdemyCloneApplication.class, args);
	}

}
