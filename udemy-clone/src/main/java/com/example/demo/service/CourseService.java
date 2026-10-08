package com.example.demo.service;

import com.example.demo.dao.*;
import com.example.demo.dto.CourseDto;
import com.example.demo.entity.*;
import com.example.demo.exception.AlreadyEnrolledException;
import com.example.demo.exception.StudentNotFoundException;
import com.example.demo.exception.TeacherNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CategoryDao categoryDao;
    private final CourseDao courseDao;
    private final TeacherDao teacherDao;
    private final StudentDao studentDao;
    private final SiteOwnerDao siteOwnerDao;
    private final StudentEnrollCourseDao studentEnrollCourseDao;

    public List<CourseDto> getAllEnrolledCourses(String username){
        return studentEnrollCourseDao.findCourseByLoginUser(username);
    }

    public List<CourseLessons> findAllCourseLessons(Long courseId) {
        return getCourse(courseId).getCourseLessonsList();
    }

    public String addLessons(CourseLessons courseLessons, Long courseId) {
        Course course = getCourse(courseId);
        course.addCourseLesson(courseLessons);
        courseDao.save(course);
        return "%s successfully added to %s course"
                .formatted(courseLessons.lessonName(), course.getTitle());
    }

    private String generateOrder(String username) {
        SecureRandom random = new SecureRandom();
        int code = random.nextInt(100000) + 100000;
        StringBuilder sb = new StringBuilder();
        sb.append("ORDER-")
                .append(username.toUpperCase())
                .append("-")
                .append(code);
        return sb.toString();
    }

    private Student findByStudentName(String studentName) {
        return studentDao.findByUsername(studentName)
                .orElseThrow(StudentNotFoundException::new);
    }

    private Course getCourse(long id) {
        return courseDao.findById(id)
                .get();
    }
    /*
     * Search Course, GenerateOrderId, Search Student
     */
    @Transactional
    public String enrolledCourse(List<Long> courseIds, String studentName) {
        Student student = findByStudentName(studentName);
        String orderId = generateOrder(studentName);
        for (Long courseId : courseIds) {
            Optional<StudentEnrollCourse> enrollCourse = studentEnrollCourseDao.testAlreadyEnrolledCourse(studentName, courseId);
            if (enrollCourse.isPresent()) {
                throw new  AlreadyEnrolledException(studentName, courseId);
            }
            Course course = getCourse(courseId);
            course.setStudentCount(course.getStudentCount() + 1);
            StudentEnrollCourse studentEnrollCourse =
                    new StudentEnrollCourse(orderId, LocalDate.now(),0);

            student.addEnrollCourse(studentEnrollCourse);
            course.addStudentEnrollCourse(studentEnrollCourse);
            studentEnrollCourseDao.save(studentEnrollCourse);

            // Calculate course.studentCount to generate, Teacher netWorth and siteOwner-platformShare to generate
            SiteOwner siteOwner = getSiteOwner();
            Teacher teacher = course.getTeacher();
            double tenPercent = course.getFees() * 0.1;
            double ninetyPercent = course.getFees() * 0.9;
            teacher.setNetWorth(teacher.getNetWorth()
                    .add(BigDecimal.valueOf(ninetyPercent)));
            siteOwner.setPlatformShare(siteOwner.getPlatformShare()
                    .add(BigDecimal.valueOf(tenPercent)));
        }
        return "%s enrolled successfully with orderId: %s"
                .formatted(studentName, orderId);
    }

    private SiteOwner getSiteOwner() {
        return siteOwnerDao.findById(3L)
                .get();
    }

    public List<CourseDto> findAllCourses() {
        return courseDao.findAllCourses();
    }

    public List<CourseDto> findAllCoursesByTeacher(String username) {
        return courseDao.findAllCoursesByTeacherName(username);
    }

    @Transactional
    public String createCourse(
            String title,
            double fees,
            String description,
            String categoryName,
            MultipartFile image,
            String teacherName) throws IOException {
        Category category = getCategoryByName(categoryName);
        Teacher teacher = getTeacherByName(teacherName);
        Course course = new Course();
        course.setTitle(title);
        course.setFees(fees);
        course.setDescription(description);
        if (image != null) {
            course.setImage(image.getBytes());
        }
        teacher.addCourse(course);
        category = categoryDao.save(category);
        category.addCourse(course);
        courseDao.save(course);
        return "%s course created successfully"
                .formatted(title);
    }

    private Category getCategoryByName(String categoryName){
        return categoryDao.findByCategoryName(categoryName)
                .orElseGet(() -> {
                    Category category = new Category();
                    category.setCategoryName(categoryName);
                    return category;
                });
    }

    private Teacher getTeacherByName(String teacherName){
        return teacherDao.findByUsername(teacherName)
                .orElseThrow(TeacherNotFoundException::new);
    }



}
