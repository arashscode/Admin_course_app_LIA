package com.aldinalj.admin_course_app.model.DTO;

import com.aldinalj.admin_course_app.model.Category;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class CourseDTO {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String code;

    @FutureOrPresent
    private LocalDate startDate;

    @Future
    private LocalDate endDate;

    @NotBlank
    private String description;

    @NotNull
    private Category category;

    // Validation: End date can not be before start date
    @AssertTrue(message = "End date must be after or equal to start date")
    private boolean isEndDateValid() {
        return endDate == null || startDate == null || !endDate.isBefore(startDate);
    }

    public CourseDTO() {}

    // Konstruktor without id (CREATE) **
    public CourseDTO(String name, String code, LocalDate startDate, LocalDate endDate, Category category, String description) {
        this.name = name;
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.description = description;
    }

    // Konstruktor with id (RETURN)
    public CourseDTO(Long id, String name, String code, LocalDate startDate, LocalDate endDate, Category category, String description) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.description = description;
    }

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @NotBlank String getCode() {
        return code;
    }

    public void setCode(@NotBlank String code) {
        this.code = code;
    }

    public @FutureOrPresent LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(@FutureOrPresent LocalDate startDate) {
        this.startDate = startDate;
    }

    public @Future LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(@Future LocalDate endDate) {
        this.endDate = endDate;
    }

    public @NotBlank String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}