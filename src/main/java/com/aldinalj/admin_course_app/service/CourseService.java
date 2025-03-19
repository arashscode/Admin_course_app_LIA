package com.aldinalj.admin_course_app.service;

import com.aldinalj.admin_course_app.model.Course;
import com.aldinalj.admin_course_app.model.Category;
import com.aldinalj.admin_course_app.model.DTO.CourseDTO;
import com.aldinalj.admin_course_app.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<CourseDTO> getCourseById(Long id) {
        return courseRepository.findById(id)
                .map(this::toDTO);
    }

    public List<CourseDTO> getCoursesByCategory(Category category) {
        return courseRepository.findByCategory(category).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<CourseDTO> searchCoursesByName(String name) {
        return courseRepository.searchByName(name).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO createOrUpdateCourse(CourseDTO courseDTO) {
        Course course = toEntity(courseDTO);
        Course savedCourse = courseRepository.save(course);
        return toDTO(savedCourse);
    }

    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
    }

    public boolean deleteCourseIfExists(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void deleteAllCourses() {
        courseRepository.deleteAll();
    }

    private Course toEntity(CourseDTO courseDTO) {
        Course course = new Course();
        if (courseDTO.getId() != null) {
            course.setId(courseDTO.getId());
        }
        course.setName(courseDTO.getName());
        course.setCode(courseDTO.getCode());
        course.setDescription(courseDTO.getDescription());
        course.setCategory(courseDTO.getCategory());
        course.setStartDate(courseDTO.getStartDate());
        course.setEndDate(courseDTO.getEndDate());
        return course;
    }

    private CourseDTO toDTO(Course course) {
        return new CourseDTO(
                course.getId(),
                course.getName(),
                course.getCode(),
                course.getStartDate(),
                course.getEndDate(),
                course.getCategory(),
                course.getDescription()
        );
    }
}