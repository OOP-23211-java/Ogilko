package com.NSU.BookingSpecialist.controller;

import com.NSU.BookingSpecialist.repository.UserRepository;
import com.NSU.BookingSpecialist.service.AppointmentService;
import com.NSU.BookingSpecialist.model.Appointment;
import com.NSU.BookingSpecialist.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final UserRepository userRepository;

    @Data
    public static class BookingRequest {
        @NotNull
        Integer specialistId;
        @NotNull
        LocalDateTime appointmentTime;
    }

    @GetMapping
    public List<Appointment> getAppointments(Principal principal) {
        User me = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException());
        return appointmentService.getUserAppointments(me.getId());
    }

    @PostMapping
    public ResponseEntity<Appointment> book(@Valid @RequestBody AppointmentController.BookingRequest request, Principal principal) {
        User me = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException());
        Appointment created = appointmentService.bookAppointment(request.getSpecialistId(), me.getId(), request.getAppointmentTime());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{idAppointment}")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Integer idAppointment, Principal principal) {
        User me = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException());
        appointmentService.cancelAppointment(idAppointment, me.getId());
        return ResponseEntity.noContent().build();
    }

}
