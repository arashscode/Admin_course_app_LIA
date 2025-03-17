package com.aldinalj.admin_course_app.service;

import com.aldinalj.admin_course_app.model.Course;
import com.aldinalj.admin_course_app.model.Category;
import com.aldinalj.admin_course_app.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Integer id) {
        return courseRepository.findById(id);
    }

    public List<Course> getCoursesByCategory(Category category) {
        return courseRepository.findByCategory(category);
    }

    public List<Course> searchCoursesByName(String name) {
        return courseRepository.searchByName(name);
    }

    public Long getCourseId(String courseName) {
        Optional<Course> optionalCourse = courseRepository.findCourseByName(courseName);
        return optionalCourse.map(Course::getId).orElse(null);
    }

    public Course createOrUpdateCourse(Course course) {
        return courseRepository.save(course);
    }

    public void deleteCourseById(Integer id) {
        courseRepository.deleteById(id);
    }

    public boolean deleteCourseIfExists(Integer id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void deleteAllCourses() {
        courseRepository.deleteAll();
    }
}