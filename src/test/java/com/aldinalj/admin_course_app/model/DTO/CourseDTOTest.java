package com.aldinalj.admin_course_app.model.DTO;

import com.aldinalj.admin_course_app.model.Category;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        System.out.println("Validator initialized for CourseDTO tests.");
    }

    @Test
    void shouldBeValidForNewCourseWithoutId() {
        System.out.println("\n🟢 Running test: shouldBeValidForNewCourseWithoutId");

        CourseDTO validCourse = new CourseDTO(
                "Java Basics",
                "JAVA101",
                LocalDate.now().plusDays(10),
                LocalDate.now().plusDays(30),
                Category.PROGRAMMING,
                "Learn Java fundamentals"
        );  // ID set automatically by constructor

        Set<ConstraintViolation<CourseDTO>> violations = validator.validate(validCourse);
        logValidationErrors(violations);

        assertTrue(violations.isEmpty(), " - A new CourseDTO should be valid without an ID");
        System.out.println("Test passed!");
    }

    @Test
    void shouldBeValidForExistingCourseWithId() {
        System.out.println("\n🟢 Running test: shouldBeValidForExistingCourseWithId");

        CourseDTO validCourse = new CourseDTO(
                1L,
                "Java Advanced",
                "JAVA201",
                LocalDate.now().plusDays(10),
                LocalDate.now().plusDays(30),
                Category.PROGRAMMING,
                "Advanced Java concepts"
        );

        Set<ConstraintViolation<CourseDTO>> violations = validator.validate(validCourse);
        logValidationErrors(violations);

        assertTrue(violations.isEmpty(), " - An existing CourseDTO should be valid with an ID");
        System.out.println("Test passed!");
    }

    @Test
    void shouldFailValidationIfNameIsBlank() {
        System.out.println("\n🟢 Running test: shouldFailValidationIfNameIsBlank");

        CourseDTO course = new CourseDTO(
                null,
                "",
                "JAVA101",
                LocalDate.now().plusDays(10),
                LocalDate.now().plusDays(30),
                Category.PROGRAMMING,
                "Description"
        );

        Set<ConstraintViolation<CourseDTO>> violations = validator.validate(course);
        logValidationErrors(violations);

        assertFalse(violations.isEmpty(), " - CourseDTO should fail validation if name is blank");
        System.out.println("Test passed!");
    }

    @Test
    void shouldFailValidationIfCodeIsBlank() {
        System.out.println("\n🟢 Running test: shouldFailValidationIfCodeIsBlank");

        CourseDTO course = new CourseDTO(
                null,
                "Java Basics",
                "",
                LocalDate.now().plusDays(10),
                LocalDate.now().plusDays(30),
                Category.PROGRAMMING,
                "Description"
        );

        Set<ConstraintViolation<CourseDTO>> violations = validator.validate(course);
        logValidationErrors(violations);

        assertFalse(violations.isEmpty(), " - CourseDTO should fail validation if code is blank");
        System.out.println("Test passed!");
    }

    @Test
    void shouldFailValidationIfEndDateIsInThePast() {
        System.out.println("\n🟢 Running test: shouldFailValidationIfEndDateIsInThePast");

        CourseDTO course = new CourseDTO(
                null,
                "Java Basics",
                "JAVA101",
                LocalDate.now().plusDays(10),
                LocalDate.now().minusDays(1),
                Category.PROGRAMMING,
                "Description"
        );

        Set<ConstraintViolation<CourseDTO>> violations = validator.validate(course);
        logValidationErrors(violations);

        assertFalse(violations.isEmpty(), " - CourseDTO should fail validation if endDate is in the past");
        System.out.println("Test passed!");
    }

    @Test
    void shouldFailValidationIfDescriptionIsBlank() {
        System.out.println("\n🟢 Running test: shouldFailValidationIfDescriptionIsBlank");

        CourseDTO course = new CourseDTO(
                null,
                "Java Basics",
                "JAVA101",
                LocalDate.now().plusDays(10),
                LocalDate.now().plusDays(30),
                Category.PROGRAMMING,
                ""
        );

        Set<ConstraintViolation<CourseDTO>> violations = validator.validate(course);
        logValidationErrors(violations);

        assertFalse(violations.isEmpty(), " - CourseDTO should fail validation if description is blank");
        System.out.println("Test passed!");
    }

    @Test
    void shouldFailValidationIfCategoryIsNull() {
        System.out.println("\n🟢 Running test: shouldFailValidationIfCategoryIsNull");

        CourseDTO course = new CourseDTO(
                null,
                "Java Basics",
                "JAVA101",
                LocalDate.now().plusDays(10),
                LocalDate.now().plusDays(30),
                null,
                "Description"
        );

        Set<ConstraintViolation<CourseDTO>> violations = validator.validate(course);
        logValidationErrors(violations);

        assertFalse(violations.isEmpty(), " - CourseDTO should fail validation if category is null");
        System.out.println("Test passed!");
    }

    private void logValidationErrors(Set<ConstraintViolation<CourseDTO>> violations) {
        System.out.println("Summary validation errors: " + violations.size());

        if (!violations.isEmpty()) {
            System.out.println("Validation error:");
            for (ConstraintViolation<CourseDTO> violation : violations) {
                System.out.println("- " + violation.getPropertyPath() + " - " + violation.getMessage());
            }
        }
    }
}