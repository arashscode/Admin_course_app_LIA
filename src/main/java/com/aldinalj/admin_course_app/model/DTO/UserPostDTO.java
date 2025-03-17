package com.aldinalj.admin_course_app.model.DTO;

import com.aldinalj.admin_course_app.model.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserPostDTO {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    @Size(min = 8)//är detta redundant?
//  @Pattern (regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]$",
    // message = "Password must contain at least one letter, one number, one special character")
    private String password;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotNull // 💡 Ändrat från String till Role, så vi använder @NotNull istället för @NotBlank
    private Role role;

    public UserPostDTO(String firstName, String lastName, String password, String email, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}