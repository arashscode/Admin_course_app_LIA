package com.aldinalj.admin_course_app.ControllerTest;

import com.aldinalj.admin_course_app.controller.CourseController;
import com.aldinalj.admin_course_app.service.CourseService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CourseService courseService;

    @BeforeEach
    void setUp() throws Exception {

        this.mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "name": "TestCourse",
                            "code": "T2026",
                            "startDate": "2026-03-07",
                            "endDate": "2099-03-07",
                            "description": "testDescription",
                            "adminId": "testAdminId"
                        }
                        """))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void courseControllerGetTest() throws Exception {
        this.mockMvc.perform(get("/api/courses"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    void courseControllerGetIdTest() throws Exception {

        Long testCourseId = courseService.getCourseId("TestCourse");


        this.mockMvc.perform(get("/api/courses/" + testCourseId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void courseControllerGetIdFailTest() throws Exception {
        this.mockMvc.perform(get("/api/courses/0"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    void CourseControllerPutTest() throws Exception{

        Long testCourseId = courseService.getCourseId("TestCourse");

        this.mockMvc.perform(put("/api/courses/" + testCourseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {              
                    "name": "TestCourse",
                    "code": "T2027",
                    "startDate": "2027-03-07",
                    "endDate": "2099-03-07",
                    "description": "testDescription",
                    "adminId": "testAdminId"
                }
                """))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @AfterEach
    void tearDown() throws Exception {
            Long testCourseId = courseService.getCourseId("TestCourse");
            this.mockMvc.perform(delete("/api/courses/" + testCourseId))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());
        }
    }




