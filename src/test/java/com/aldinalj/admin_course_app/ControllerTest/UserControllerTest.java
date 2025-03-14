package com.aldinalj.admin_course_app.ControllerTest;

import com.aldinalj.admin_course_app.model.User;
import com.aldinalj.admin_course_app.repository.UserRepository;
import com.aldinalj.admin_course_app.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
//Use this import if changing to @TestPropertySource /Arash
//import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") //  Better of option for multiple test. Otherwise, use code below. /Arash
//@TestPropertySource(locations = "classpath:application-test.properties")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager; // Executing SQL directly

    private Long testUserId;

    @BeforeEach
    @WithMockUser(roles = "ADMIN")
    void setUp() throws Exception {
        userRepository.deleteAll();

        // CREATE
        MvcResult result = this.mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "Håkan",
                                    "lastName": "Gleissman",
                                    "email": "hakan.gleissman@sti.se",
                                    "password": "password123",
                                    "role": "ADMIN"
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        // GET
        Optional<User> optionalUser = userService.getUserByEmail("hakan.gleissman@sti.se");

        if (optionalUser.isPresent()) {
            testUserId = optionalUser.get().getId();
        } else {
            throw new RuntimeException("Testanvändaren kunde inte skapas.");
        }
    }


    // READ
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnAllUsers() throws Exception {
        this.mockMvc.perform(get("/api/users"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // READ; Get with ID
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnUserById() throws Exception {
        this.mockMvc.perform(get("/api/users/" + testUserId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // READ; Get non-existence (404 IF not found)
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnNotFoundForInvalidUserId() throws Exception {
        this.mockMvc.perform(get("/api/users/0"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // UPDATE; Update & Control
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateUser() throws Exception {
        this.mockMvc.perform(put("/api/users/" + testUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "Martin",
                                    "lastName": "Jernström",
                                    "email": "martin.jernstrom@stud.sti.se",
                                    "password": "newpassword123",
                                    "role": "USER"
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Get user & print
        Optional<User> updatedUser = userService.getUserById(testUserId);
        if (updatedUser.isPresent()) {
            User user = updatedUser.get();
            System.out.println("User is updated:");
            System.out.println("ID: " + user.getId() +
                    ", Name: " + user.getFirstName() + " " + user.getLastName() +
                    ", Email: " + user.getEmail() +
                    ", Role: " + user.getRole());
        } else {
            System.out.println("User could not be found after update!");
        }
    }

    // DELETE; Delete User (in @AfterEach)
    @AfterEach
    @WithMockUser(roles = "ADMIN")
    void tearDown() throws Exception {
        this.mockMvc.perform(delete("/api/users/" + testUserId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    // DEBUG; Print all users in database
    @Test
    @WithMockUser(roles = "ADMIN")
    void debugPrintUsers() {
        System.out.println("Users in database:");
        userRepository.findAll().forEach(user ->
                System.out.println("ID: " + user.getId() +
                        ", Name: " + user.getFirstName() + " " + user.getLastName() +
                        ", Email: " + user.getEmail() +
                        ", Role: " + user.getRole())
        );
    }

    // DEBUG: Print all tables in H2
    @Test
    @WithMockUser(roles = "ADMIN")
    void debugPrintTables() {
        System.out.println("Tables in database:");

        @SuppressWarnings("unchecked") // Ignore unchecked warnings
        List<Object[]> tables = (List<Object[]>) entityManager.createNativeQuery("SHOW TABLES").getResultList();

        tables.forEach(table -> {
            if (table instanceof Object[]) {
                System.out.println(((Object[]) table)[0]);
            } else {
                System.out.println(table.toString());
            }
        });
    }
}