package com.aldinalj.admin_course_app.repository;

import com.aldinalj.admin_course_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Get by username
    Optional<User> findByEmail(String email);
}