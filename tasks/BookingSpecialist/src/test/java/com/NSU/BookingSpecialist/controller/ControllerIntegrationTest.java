package com.NSU.BookingSpecialist.controller;

import com.NSU.BookingSpecialist.model.Specialist;
import com.NSU.BookingSpecialist.repository.AppointmentRepository;
import com.NSU.BookingSpecialist.repository.SpecialistRepository;
import com.NSU.BookingSpecialist.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpecialistRepository specialistRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        specialistRepository.deleteAll();
        appointmentRepository.deleteAll();

        specialistRepository.save(new Specialist(null, "Dima", "Programmer"));
    }

    @Test
    void signupAndGetSpecialist() throws Exception {
        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@test.com\",\"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@test.com"));

        mockMvc.perform(get("/api/specialists")
                .with(httpBasic("test@test.com", "test")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("Dima"))
                .andExpect(jsonPath("$.[0].profession").value("Programmer"));
    }

    @Test
    void bookAndCancelAppointment() throws Exception {
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"user@example.com\",\"password\":\"pass\"}"))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/appointments")
                        .with(httpBasic("user@example.com", "pass"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"specialistId\":1,\"appointmentTime\":\"2025-06-10T15:00:00\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        mockMvc.perform(get("/api/appointments")
                        .with(httpBasic("user@example.com", "pass")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].specialistId").value(1));

        mockMvc.perform(delete("/api/appointments/1")
                        .with(httpBasic("user@example.com", "pass")))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
