package com.aldinalj.admin_course_app.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "courses", indexes = {
        @Index(name = "idx_course_name", columnList = "name")  // 🔥 Skapar ett index på "name"
})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Changed to IDENTITY for compability MySQL/H2 AUTO_INCREMENT on id
    private Long id;

    private String name;
    private String code;
    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String description;



    public Course() {}

    // Constructor for instansation
    public Course(String name, String code, LocalDate startDate, LocalDate endDate, String description, Category category) {
        this.name = name;
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.description = description;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}