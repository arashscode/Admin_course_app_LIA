package com.aldinalj.admin_course_app.ControllerTest;

import com.aldinalj.admin_course_app.controller.UserController;
import com.aldinalj.admin_course_app.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private Long testUserId;

    @BeforeEach
    void setUp() throws Exception {
        // Skapa en testanvändare via API
        String response = this.mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "firstName": "John",
                            "lastName": "Doe",
                            "email": "johndoe@example.com",
                            "password": "password123",
                            "role": "USER"
                        }
                        """))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();


        testUserId = userService.getUserByEmail("johndoe@example.com").get().getId();
    }

    @Test
    void userControllerGetTest() throws Exception {
        this.mockMvc.perform(get("/api/users"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void userControllerGetByIdTest() throws Exception {
        this.mockMvc.perform(get("/api/users/" + testUserId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void userControllerGetByIdFailTest() throws Exception {
        this.mockMvc.perform(get("/api/users/0"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void userControllerPutTest() throws Exception {
        this.mockMvc.perform(put("/api/users/" + testUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "firstName": "Jane",
                            "lastName": "Doe",
                            "email": "janedoe@example.com",
                            "password": "newpassword123",
                            "role": "ADMIN"
                        }
                        """))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @AfterEach
    void tearDown() throws Exception {
        this.mockMvc.perform(delete("/api/users/" + testUserId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}