package com.NSU.BookingSpecialist.service;

import com.NSU.BookingSpecialist.model.Appointment;
import com.NSU.BookingSpecialist.repository.AppointmentRepository;
import com.NSU.BookingSpecialist.repository.SpecialistRepository;
import com.NSU.BookingSpecialist.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final SpecialistRepository specialistRepository;
    private final UserRepository userRepository;

    @Transactional
    public Appointment bookAppointment(Integer specialistId, Integer userId, LocalDateTime time) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User with id " + userId + " not found");
        }

        if (!specialistRepository.existsById(specialistId)) {
            throw new EntityNotFoundException("Specialist with id " + specialistId + " not found");
        }

        if (appointmentRepository.existsBySpecialistIdAndAppointmentTime(specialistId, time)) {
            throw new IllegalStateException("Appointment with id " + specialistId + " is busy at " + time);
        }

        Appointment appointment = new Appointment();
        appointment.setSpecialistId(specialistId);
        appointment.setUserId(userId);
        appointment.setAppointmentTime(time);
        appointment.setStatus("ACTIVE");

        return appointmentRepository.save(appointment);
    }

    @Transactional
    public void cancelAppointment(Integer appointmentId, Integer userId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment with id " + appointmentId + " not found"));

        if (!appointment.getUserId().equals(userId)) {
            throw new SecurityException("You can't cancel someone else's account.");
        }

        if (appointment.getStatus().equals("CANCELLED")) {
            throw new IllegalStateException("The recording has already been canceled.");
        }

        appointment.setStatus("CANCELLED");
        appointmentRepository.save(appointment);
    }

    public List<Appointment> getUserAppointments(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User with id " + userId + " not found");
        }
        return appointmentRepository.findByUserId(userId);
    }
}
