package com.aldinalj.admin_course_app.service;

import com.aldinalj.admin_course_app.model.DTO.UserGetDTO;
import com.aldinalj.admin_course_app.model.DTO.UserPostDTO;
import com.aldinalj.admin_course_app.model.Role;
import com.aldinalj.admin_course_app.model.User;
import com.aldinalj.admin_course_app.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Hämta alla användare
    public List<UserGetDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Hämta en användare baserat på ID
    public Optional<UserGetDTO> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(this::toDTO);
    }

    // Hämta en användare baserat på e-post
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Skapa en ny användare
    public User createUser(UserPostDTO userPostDTO) {
        userPostDTO.setPassword(passwordEncoder.encode(userPostDTO.getPassword()));
        User userToEntity = toEntity(userPostDTO);
        return userRepository.save(userToEntity);
    }

    // Uppdatera en användare
    public UserGetDTO updateUser(Long id, UserPostDTO updatedUserDTO) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(updatedUserDTO.getFirstName());
                    user.setLastName(updatedUserDTO.getLastName());
                    user.setEmail(updatedUserDTO.getEmail());
                    user.setPassword(passwordEncoder.encode(updatedUserDTO.getPassword()));
                    user.setRole(updatedUserDTO.getRole()); // 💡 Lägger till roll-uppdatering
                    userRepository.save(user);
                    return toDTO(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Radera en användare
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    public void createOrUpdateUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    // 💡 Uppdaterad toDTO-metod som inkluderar role
    private UserGetDTO toDTO(User user) {
        return new UserGetDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole() // 💡 Lägger till role i DTO
        );
    }

    private User toEntity(UserPostDTO userPostDTO) {
        return new User(
                userPostDTO.getFirstName(),
                userPostDTO.getLastName(),
                userPostDTO.getPassword(),
                userPostDTO.getEmail(),
                userPostDTO.getRole() // 💡 Se till att role följer enum
        );
    }
}