package com.aldinalj.admin_course_app.controller;
import com.aldinalj.admin_course_app.model.Course;
import com.aldinalj.admin_course_app.repository.CourseRepository;
import com.aldinalj.admin_course_app.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;
    private final CourseRepository courseRepository;

    public CourseController(CourseService courseService, CourseRepository courseRepository) {
        this.courseService = courseService;
        this.courseRepository = courseRepository;
    }

    @Operation(summary = "Create course")
    @PostMapping
    public ResponseEntity<?> createOrUpdateCourse(@RequestBody @Valid Course course) {
        try {
            Course savedCourse = courseService.createOrUpdateCourse(course);
            return ResponseEntity.created(URI.create("/api/courses/" + savedCourse.getId())).body(savedCourse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    //TODO
    //add 404 IF NOT FOUND

    @Operation(summary = "Update existing course in database")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCourse(@PathVariable Integer id, @RequestBody Course updatedCourse) {

        Optional<Course> course = courseService.getCourseById(id);

        Course existingCourse = course.get();

        existingCourse.setName(updatedCourse.getName());
        existingCourse.setCode(updatedCourse.getCode());
        existingCourse.setStartDate(updatedCourse.getStartDate());
        existingCourse.setEndDate(updatedCourse.getEndDate());
        existingCourse.setDescription(updatedCourse.getDescription());

        courseService.createOrUpdateCourse(existingCourse);

        return ResponseEntity.status(200).body("Course updated successfully.");
    }

    @Operation(summary = "Deletes course from database")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Integer id) {
        try {
            if (courseRepository.existsById(id)) {
                courseRepository.deleteById(id);
                return ResponseEntity.status(204).body("Course deleted successfully.");
            } else {
                return ResponseEntity.status(404).body("Course not found.");
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @Operation(summary = "Get all courses from database")
    @GetMapping
    public ResponseEntity<List<Course>> getCourse(){
        List<Course> courses = courseService.getAllCourses();

        if (courses.isEmpty()){
            return ResponseEntity.status(404).body(courses);
        }

        return ResponseEntity.status(200).body(courses);

    }


    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Integer id){
        Optional<Course> optionalCourse = courseService.getCourseById(id);
        if(optionalCourse.isEmpty()){
            return ResponseEntity.status(404).body(null);
        }

        return ResponseEntity.status(200).body(optionalCourse.get());
    }



}