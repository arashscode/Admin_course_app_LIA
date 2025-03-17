package com.aldinalj.admin_course_app.controller;

import com.aldinalj.admin_course_app.model.DTO.UserGetDTO;
import com.aldinalj.admin_course_app.model.DTO.UserPostDTO;
import com.aldinalj.admin_course_app.model.User;
import com.aldinalj.admin_course_app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<List<UserGetDTO>> getAllUsers() {
        List<UserGetDTO> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.status(404).body(users);
        } else return ResponseEntity.status(200).body(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<UserGetDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserPostDTO user) {
        try {
            User savedUser = userService.createUser(user);
            return ResponseEntity.created(URI.create("/api/users/" + savedUser.getId())).body(savedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing user")
    public ResponseEntity<UserGetDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserPostDTO updatedUser) {
        if(!userService.getUserById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        } else return ResponseEntity.ok(userService.updateUser(id, updatedUser));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if(!userService.getUserById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}