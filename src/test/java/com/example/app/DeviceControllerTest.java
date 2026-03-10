package com.example.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getDevice_returnsDevice() throws Exception {
        mockMvc.perform(get("/getDevice").param("id", "123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("123"))
                .andExpect(jsonPath("$[0].name").value("Lights"));
    }

    @Test
    public void updateDevice_returnsCreated() throws Exception {
        String body = "{\"id\":\"123\",\"name\":\"Lights\",\"status\":\"on\"}";
        mockMvc.perform(post("/updateDevice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.status").value("on"))
                .andExpect(jsonPath("$.lastUpdated").isNotEmpty());
    }

    @Test
    public void getDevice_unknownId_returnsNotFound() throws Exception {
        mockMvc.perform(get("/getDevice").param("id", "unknown"))
                .andExpect(status().isNotFound());
    }
}
