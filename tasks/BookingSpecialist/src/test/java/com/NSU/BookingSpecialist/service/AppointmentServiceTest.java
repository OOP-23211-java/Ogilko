package com.NSU.BookingSpecialist.service;

import com.NSU.BookingSpecialist.model.Appointment;
import com.NSU.BookingSpecialist.repository.AppointmentRepository;
import com.NSU.BookingSpecialist.repository.SpecialistRepository;
import com.NSU.BookingSpecialist.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class AppointmentServiceTest {
    private UserRepository userRepository;
    private SpecialistRepository specialistRepository;
    private AppointmentRepository appointmentRepository;
    private AppointmentService appointmentService;

    private final Integer userId = 1;
    private final Integer specialistId = 1;
    private final LocalDateTime appointmentTime = LocalDateTime.of(2020, 1, 1, 0, 0);

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        specialistRepository = mock(SpecialistRepository.class);
        appointmentRepository = mock(AppointmentRepository.class);
        appointmentService = new AppointmentService(appointmentRepository, specialistRepository, userRepository);
    }

    @Test
    void bookAppointment_success() {
        when(userRepository.existsById(userId)).thenReturn(true);
        when(specialistRepository.existsById(specialistId)).thenReturn(true);
        when(appointmentRepository.existsBySpecialistIdAndAppointmentTime(specialistId, appointmentTime)).thenReturn(false);

        Appointment savedAppointment = new Appointment();
        savedAppointment.setId(1);
        savedAppointment.setUserId(userId);
        savedAppointment.setSpecialistId(specialistId);
        savedAppointment.setAppointmentTime(appointmentTime);
        savedAppointment.setStatus("ACTIVE");

        when(appointmentRepository.save(any(Appointment.class))).thenReturn(savedAppointment);
        Appointment appointment = appointmentService.bookAppointment(userId, specialistId, appointmentTime);
        assertEquals(appointment.getStatus(), "ACTIVE");
        assertEquals(appointment.getId(), 1);
    }

    @Test
    void bookAppointment_userNotFound() {
        when(userRepository.existsById(userId)).thenReturn(false);
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> appointmentService.bookAppointment(userId, specialistId, appointmentTime));
        assertEquals("User with id " + userId + " not found", ex.getMessage());
    }

    @Test
    void bookAppointment_specialistNotFound() {
        when(userRepository.existsById(userId)).thenReturn(true);
        when(specialistRepository.existsById(specialistId)).thenReturn(false);
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> appointmentService.bookAppointment(userId, specialistId, appointmentTime));
        assertEquals("Specialist with id " + specialistId + " not found", ex.getMessage());
    }
}
