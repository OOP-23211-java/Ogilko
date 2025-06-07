package com.NSU.BookingSpecialist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.NSU.BookingSpecialist.model.Appointment;

import java.time.LocalDateTime;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    boolean existsBySpecialistIdAndAppointmentTime(Integer specialistId, LocalDateTime appointmentTime);

    List<Appointment> findByUserId(Integer UserId);

    List<Appointment> findBySpecialistIdAndStatus(Integer specialistId, String status);
}
