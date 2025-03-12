package com.aldinalj.admin_course_app.config;

import com.aldinalj.admin_course_app.model.Course;
import com.aldinalj.admin_course_app.model.User;
import com.aldinalj.admin_course_app.repository.CourseRepository;
import com.aldinalj.admin_course_app.service.CourseService;
import com.aldinalj.admin_course_app.service.UserService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    public ApplicationRunner initDatabase(CourseService courseService, UserService userService) {
        return args -> {
            if (courseService.getAllCourses().isEmpty()) {
                Course dummyCourse = new Course();
                dummyCourse.setName("Dummy");
                dummyCourse.setCode("Dummy");
                dummyCourse.setStartDate(LocalDate.now());
                dummyCourse.setEndDate(LocalDate.of(2099, 12, 31));
                dummyCourse.setDescription("This is a dummy course");
                dummyCourse.setAdminId("DummyID");

                courseService.createOrUpdateCourse(dummyCourse);
                System.out.println("Dummy course added!");
            } else {
                System.out.println("Database already contains courses");
            }

            if (userService.getAllUsers().isEmpty()){
                User dummyUser = new User();
                dummyUser.setRole("ADMIN");
                dummyUser.setFirstName("dummy");
                dummyUser.setLastName("dummy");
                dummyUser.setPassword("DummyPassword");
                dummyUser.setEmail("dummy@dummy.com");

                userService.createOrUpdateUser(dummyUser);
                System.out.println("Dummy user added");
            }
            else {
                System.out.println("Database already contains users");
            }
        };
    }
}
