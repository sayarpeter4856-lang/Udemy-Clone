package com.example.demo.security;

import com.example.demo.dao.RoldeDao;
import com.example.demo.dao.SiteOwnerDao;
import com.example.demo.dao.StudentDao;
import com.example.demo.dao.TeacherDao;
import com.example.demo.dto.RegisterDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.Student;
import com.example.demo.entity.StudentEducation;
import com.example.demo.entity.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;
    private final StudentDao studentDao;
    private final TeacherDao teacherDao;
    private final SiteOwnerDao siteOwnerDao;
    private final RoldeDao roldeDao;

    private Role getRoleByName (String roleName) {
        return roldeDao.findByRoleName(roleName)
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setRoleName(roleName);
                    return role;
                });
    }

    public String register(RegisterDto registerDto) {
        String returnString = switch (registerDto.userType()){
            case "teacher" -> {
                var teacher = new Teacher(
                        registerDto.username(),
                        passwordEncoder.encode(registerDto.password()),
                        registerDto.email(),
                        BigDecimal.valueOf(0),
                        registerDto.education());
                teacher.setSkills(registerDto.skill());
                Role teacherRole = getRoleByName("ROLE_TEACHER");
                teacher.addRole(roldeDao.save(teacherRole));
                teacherDao.save(teacher);
                yield "%s register as teacher successfully!"
                        .formatted(registerDto.username());
            }
            case  "student" -> {
                var student = new Student(
                        registerDto.username(),
                        passwordEncoder.encode(registerDto.password()),
                        registerDto.email(),
                        registerDto.address(),
                        StudentEducation.valueOf(registerDto.studentEducation())
                );
                Role studentRole = getRoleByName("ROLE_STUDENT");
                student.addRole(roldeDao.save(studentRole));
                studentDao.save(student);
                yield  "%s register as student successfully!".formatted(registerDto.username());
            }
            default ->
                throw new IllegalArgumentException("Invalid user type");
        };
        return returnString;
    }

    public Map<String, String> login(String username, String password) {
        var auth = new UsernamePasswordAuthenticationToken(username, password);
        var authentication = authenticationManager.authenticate(auth);
        StringBuilder sb = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(r -> {
                    StringBuilder s = new StringBuilder();
                    s.append(r);
                    return s;
                })
                .findAny().get();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return Map.of("username", auth.getName(),
                "roles", sb.toString());
    }
}
