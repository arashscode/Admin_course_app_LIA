package com.aldinalj.admin_course_app.model.DTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class CourseDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String code;
    @FutureOrPresent
    private LocalDate startDate;
    @Future
    private LocalDate endDate;

    public CourseDTO(String name, String code, LocalDate startDate, LocalDate endDate, String description) {
        this.name = name;
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    @NotBlank
    private String description;

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
}
