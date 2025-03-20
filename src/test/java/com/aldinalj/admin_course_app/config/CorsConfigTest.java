package com.aldinalj.admin_course_app.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CorsConfigTest {

    @Autowired
    private MockMvc mockMvc;
// Testar om CORS tillåter anrop på localhost:5273 (för frontend) med GET, POST, PUT, DELETE, OPTIONS. /A

    @Test
    void testCorsHeadersForAllMethods() throws Exception {
        String[] methods = {"GET", "POST", "PUT", "DELETE", "OPTIONS"};

        for (String method : methods) {
            mockMvc.perform(options("/api/test")
                            .header("Origin", "http://localhost:5173")
                            .header("Access-Control-Request-Method", method))
                    .andExpect(status().isOk())
                    .andExpect(header().exists("Access-Control-Allow-Origin"))
                    .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:5173"))
                    .andExpect(header().string("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS"));
        }
    }
}