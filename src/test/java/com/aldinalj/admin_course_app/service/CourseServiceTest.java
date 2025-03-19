package com.aldinalj.admin_course_app.service;

import com.aldinalj.admin_course_app.model.Category;
import com.aldinalj.admin_course_app.model.Course;
import com.aldinalj.admin_course_app.model.DTO.CourseDTO;
import com.aldinalj.admin_course_app.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    private Course dummyCourse;
    private CourseDTO dummyCourseDTO;

    @BeforeEach
    void setUp() {
        dummyCourse = new Course();
        dummyCourse.setId(1L);
        dummyCourse.setName("Java Basics");
        dummyCourse.setCode("JAVA101");
        dummyCourse.setStartDate(LocalDate.now().plusDays(10));
        dummyCourse.setEndDate(LocalDate.now().plusDays(30));
        dummyCourse.setCategory(Category.PROGRAMMING);
        dummyCourse.setDescription("Learn Java fundamentals");

        dummyCourseDTO = new CourseDTO(
                1L,
                "Java Basics",
                "JAVA101",
                LocalDate.now().plusDays(10),
                LocalDate.now().plusDays(30),
                Category.PROGRAMMING,
                "Learn Java fundamentals"
        );
    }

    @Test
    void shouldReturnAllCourses() {
        System.out.println("\n🟢 Running test: shouldReturnAllCourses");
        when(courseRepository.findAll()).thenReturn(List.of(dummyCourse));

        List<CourseDTO> result = courseService.getAllCourses();
        logResult("Total courses found", result.size());

        assertFalse(result.isEmpty(), " - Course list should not be empty");
        assertEquals(1, result.size());
        System.out.println("Test passed!");
    }

    // ✅ Test för getCourseById()
    @Test
    void shouldReturnCourseByIdIfExists() {
        System.out.println("\n Running test: shouldReturnCourseByIdIfExists");
        when(courseRepository.findById(1L)).thenReturn(Optional.of(dummyCourse));

        Optional<CourseDTO> result = courseService.getCourseById(1L);
        logResult("Course found", result.isPresent());

        assertTrue(result.isPresent());
        System.out.println("Test passed!");
    }

    @Test
    void shouldReturnEmptyIfCourseNotFoundById() {
        System.out.println("\n🟢 Running test: shouldReturnEmptyIfCourseNotFoundById");
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<CourseDTO> result = courseService.getCourseById(99L);
        logResult("Course found", result.isPresent());

        assertFalse(result.isPresent());
        System.out.println("Test passed!");
    }

    @Test
    void shouldReturnCoursesBySearchName() {
        System.out.println("\n🟢 Running test: shouldReturnCoursesBySearchName");
        when(courseRepository.searchByName("Java")).thenReturn(List.of(dummyCourse));

        List<CourseDTO> result = courseService.searchCoursesByName("Java");
        logResult("Total courses found", result.size());

        assertFalse(result.isEmpty());
        System.out.println("✅ Test passed!");
    }


    @Test
    void shouldCreateOrUpdateCourse() {
        System.out.println("\n🟢 Running test: shouldCreateOrUpdateCourse");
        when(courseRepository.save(any(Course.class))).thenReturn(dummyCourse);

        CourseDTO result = courseService.createOrUpdateCourse(dummyCourseDTO);
        logResult("Created course name", result.getName());

        assertNotNull(result);
        assertEquals(dummyCourse.getName(), result.getName());
        System.out.println("Test passed!");
    }

    @Test
    void shouldDeleteCourseById() {
        System.out.println("\n Running test: shouldDeleteCourseById");
        doNothing().when(courseRepository).deleteById(1L);

        courseService.deleteCourseById(1L);
        verify(courseRepository, times(1)).deleteById(1L);

        System.out.println(" Test passed!");
    }

    @Test
    void shouldDeleteCourseIfExists() {
        System.out.println("\n Running test: shouldDeleteCourseIfExists");
        when(courseRepository.existsById(1L)).thenReturn(true);
        doNothing().when(courseRepository).deleteById(1L);

        boolean deleted = courseService.deleteCourseIfExists(1L);
        logResult("Course deleted", deleted);

        assertTrue(deleted);
        System.out.println("Test passed!");
    }

    @Test
    void shouldNotDeleteCourseIfNotExists() {
        System.out.println("\n Running test: shouldNotDeleteCourseIfNotExists");
        when(courseRepository.existsById(99L)).thenReturn(false);

        boolean deleted = courseService.deleteCourseIfExists(99L);
        logResult("Course deleted", deleted);

        assertFalse(deleted);
        System.out.println("Test passed!");
    }

    @Test
    void shouldDeleteAllCourses() {
        System.out.println("\n Running test: shouldDeleteAllCourses");
        doNothing().when(courseRepository).deleteAll();

        courseService.deleteAllCourses();
        verify(courseRepository, times(1)).deleteAll();

        System.out.println("Test passed!");
    }

    private void logResult(String message, Object value) {
        System.out.println("🔍 " + message + ": " + value);
    }
}