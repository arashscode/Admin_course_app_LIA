package com.aldinalj.admin_course_app.ControllerTest;

import com.aldinalj.admin_course_app.model.Category;
import com.aldinalj.admin_course_app.model.DTO.CourseDTO;
import com.aldinalj.admin_course_app.service.CourseService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)  // 🔥 Säkerställer testordning
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseService courseService;

    @PersistenceContext
    private EntityManager entityManager;

    private static Long testCourseId;

    @BeforeEach
    @WithMockUser(roles = "ADMIN")
    void setUp() throws Exception {
        System.out.println("\n Setting up test data...");

        MvcResult result = this.mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Java Basics",
                                    "code": "JAVA101",
                                    "startDate": "2025-04-01",
                                    "endDate": "2025-06-01",
                                    "category": "PROGRAMMING",
                                    "description": "Learn Java fundamentals"
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        // GET ID from DB. Also trying out tests by order
        Optional<CourseDTO> optionalCourse = courseService.getAllCourses()
                .stream().filter(c -> c.getName().equals("Java Basics")).findFirst();

        if (optionalCourse.isPresent()) {
            testCourseId = optionalCourse.get().getId();
            System.out.println("Test course created with ID: " + testCourseId);
        } else {
            throw new RuntimeException("Test course could not be created.");
        }
    }

    @Test
    @Order(1)
    @WithMockUser(roles = "ADMIN")
    void shouldReturnAllCourses() throws Exception {
        System.out.println("\n🟢 Running test: shouldReturnAllCourses");
        this.mockMvc.perform(get("/api/courses"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        System.out.println("✅ Test passed!");
    }

    @Test
    @Order(2)
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCourseById() throws Exception {
        System.out.println("\n🟢 Running test: shouldReturnCourseById");
        this.mockMvc.perform(get("/api/courses/" + testCourseId))
                .andExpect(MockMvcResultMatchers.status().isOk());
        System.out.println("✅ Test passed!");
    }

    @Test
    @Order(3)
    @WithMockUser(roles = "ADMIN")
    void shouldReturnNotFoundForInvalidCourseId() throws Exception {
        System.out.println("\n🟢 Running test: shouldReturnNotFoundForInvalidCourseId");
        this.mockMvc.perform(get("/api/courses/0"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        System.out.println("✅ Test passed!");
    }

    @Test
    @Order(4)
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCoursesByCategory() throws Exception {
        System.out.println("\n🟢 Running test: shouldReturnCoursesByCategory");
        this.mockMvc.perform(get("/api/courses?category=PROGRAMMING"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        System.out.println("✅ Test passed!");
    }

    @Test
    @Order(5)
    @WithMockUser(roles = "ADMIN")
    void shouldReturnCoursesBySearchName() throws Exception {
        System.out.println("\n🟢 Running test: shouldReturnCoursesBySearchName");
        this.mockMvc.perform(get("/api/courses?search=Java"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        System.out.println("✅ Test passed!");
    }

    @Test
    @Order(6)
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateCourse() throws Exception {
        System.out.println("\n🟢 Running test: shouldUpdateCourse");
        this.mockMvc.perform(put("/api/courses/" + testCourseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Advanced Java",
                                    "code": "JAVA201",
                                    "startDate": "2025-05-01",
                                    "endDate": "2025-07-01",
                                    "category": "PROGRAMMING",
                                    "description": "Advanced Java concepts"
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Optional<CourseDTO> updatedCourse = courseService.getCourseById(testCourseId);
        updatedCourse.ifPresent(course -> {
            System.out.println("🔍 Updated Course:");
            System.out.println("ID: " + course.getId() + ", Name: " + course.getName());
        });

        System.out.println("✅ Test passed!");
    }


    @AfterEach
    @WithMockUser(roles = "ADMIN")
    void tearDown() throws Exception {
        System.out.println("\n🔥 Cleaning up test data...");
        this.mockMvc.perform(delete("/api/courses/" + testCourseId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        System.out.println("✅ Cleanup done!");
    }

    @Test
    @Order(7)
    @WithMockUser(roles = "ADMIN")
    void debugPrintCourses() {
        System.out.println("\n🟢 Running debugPrintCourses:");
        courseService.getAllCourses().forEach(course ->
                System.out.println("ID: " + course.getId() +
                        ", Name: " + course.getName() +
                        ", Category: " + course.getCategory()));
    }

    @Test
    @Order(8)
    @WithMockUser(roles = "ADMIN")
    void debugPrintTables() {
        System.out.println("\n🟢 Running debugPrintTables:");
        @SuppressWarnings("unchecked")
        var tables = (java.util.List<Object[]>) entityManager.createNativeQuery("SHOW TABLES").getResultList();
        tables.forEach(table -> System.out.println("Table: " + table[0]));
    }
}