package com.aldinalj.admin_course_app.controller;

import com.aldinalj.admin_course_app.model.Category;
import com.aldinalj.admin_course_app.model.DTO.CourseDTO;
import com.aldinalj.admin_course_app.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    @Operation(summary = "Get all courses", description = "Retrieves all courses or filters by name or category")
    public ResponseEntity<List<CourseDTO>> getCourses(@RequestParam(required = false) String search,
                                                      @RequestParam(required = false) Category category) {
        List<CourseDTO> courses;
        if (search != null) {
            courses = courseService.searchCoursesByName(search);
        } else if (category != null) {
            courses = courseService.getCoursesByCategory(category);
        } else {
            courses = courseService.getAllCourses();
        }

        if (courses.isEmpty()) {
            return ResponseEntity.status(404).body(courses);
        } else {
            return ResponseEntity.status(200).body(courses);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course by ID", description = "Retrieves a course by its unique ID")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        Optional<CourseDTO> courseDTO = courseService.getCourseById(id);
        return courseDTO.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }

    @PostMapping
    @Operation(summary = "Create a new course", description = "Adds a new course to the system")
    public ResponseEntity<?> createCourse(@RequestBody CourseDTO courseDTO) {
        try {
            CourseDTO savedCourse = courseService.createOrUpdateCourse(courseDTO);
            return ResponseEntity.status(201).body(savedCourse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing course", description = "Updates a course with new data")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id, @RequestBody CourseDTO courseDTO) {
        if (courseService.getCourseById(id).isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        courseDTO.setId(id);
        CourseDTO updatedCourse = courseService.createOrUpdateCourse(courseDTO);
        return ResponseEntity.status(200).body(updatedCourse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a course", description = "Removes a course from the system")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        if (courseService.getCourseById(id).isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        courseService.deleteCourseById(id);
        return ResponseEntity.status(204).build(); //Status 204 = "No Content"
    }
}