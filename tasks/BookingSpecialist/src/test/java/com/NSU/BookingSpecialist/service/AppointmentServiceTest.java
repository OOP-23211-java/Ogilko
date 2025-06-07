package com.NSU.BookingSpecialist.service;

import com.NSU.BookingSpecialist.model.Appointment;
import com.NSU.BookingSpecialist.repository.AppointmentRepository;
import com.NSU.BookingSpecialist.repository.SpecialistRepository;
import com.NSU.BookingSpecialist.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class AppointmentServiceTest {
    private UserRepository userRepository;
    private SpecialistRepository specialistRepository;
    private AppointmentRepository appointmentRepository;
    private AppointmentService appointmentService;

    private final Integer userId = 1;
    private final Integer specialistId = 1;
    private final Integer appointmentId = 1;
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

    @Test
    void bookAppointment_slotOccupied() {
        when(userRepository.existsById(userId)).thenReturn(true);
        when(specialistRepository.existsById(specialistId)).thenReturn(true);
        when(appointmentRepository.existsBySpecialistIdAndAppointmentTime(specialistId, appointmentTime))
                .thenReturn(true);

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> appointmentService.bookAppointment(userId, specialistId, appointmentTime));
        assertEquals("Appointment with id " + specialistId + " is busy at " + appointmentTime, ex.getMessage());
    }

    @Test
    void cancelAppointment_success() {
        Appointment appt = new Appointment();
        appt.setId(appointmentId);
        appt.setUserId(userId);
        appt.setSpecialistId(specialistId);
        appt.setAppointmentTime(appointmentTime);
        appt.setStatus("ACTIVE");
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appt));

        appointmentService.cancelAppointment(appointmentId, userId);

        ArgumentCaptor<Appointment> captor = ArgumentCaptor.forClass(Appointment.class);
        verify(appointmentRepository).save(captor.capture());
        assertEquals("CANCELLED", captor.getValue().getStatus());
    }

    @Test
    void cancelAppointment_notFound() {
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> appointmentService.cancelAppointment(appointmentId, userId));
        assertEquals("Appointment with id " + appointmentId + " not found", ex.getMessage());
    }

    @Test
    void cancelAppointment_wrongUser() {
        Appointment appt = new Appointment();
        appt.setId(appointmentId);
        appt.setUserId(userId + 1);
        appt.setSpecialistId(specialistId);
        appt.setAppointmentTime(appointmentTime);
        appt.setStatus("ACTIVE");
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appt));

        SecurityException ex = assertThrows(SecurityException.class,
                () -> appointmentService.cancelAppointment(appointmentId, userId));
        assertEquals("You can't cancel someone else's account.", ex.getMessage());
    }

    @Test
    void cancelAppointment_alreadyCancelled() {
        Appointment appt = new Appointment();
        appt.setId(appointmentId);
        appt.setUserId(userId);
        appt.setSpecialistId(specialistId);
        appt.setAppointmentTime(appointmentTime);
        appt.setStatus("CANCELLED");
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appt));

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> appointmentService.cancelAppointment(appointmentId, userId));
        assertEquals("The recording has already been canceled.", ex.getMessage());
    }

    @Test
    void getUserAppointments_success() {
        when(userRepository.existsById(userId)).thenReturn(true);
        Appointment appt1 = new Appointment(); appt1.setId(1);
        Appointment appt2 = new Appointment(); appt2.setId(2);
        when(appointmentRepository.findByUserId(userId))
                .thenReturn(List.of(appt1, appt2));

        List<Appointment> result = appointmentService.getUserAppointments(userId);
        assertEquals(2, result.size());
    }

    @Test
    void getUserAppointments_userNotFound() {
        when(userRepository.existsById(userId)).thenReturn(false);
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> appointmentService.getUserAppointments(userId));
        assertEquals("User with id " + userId + " not found", ex.getMessage());
    }
}
