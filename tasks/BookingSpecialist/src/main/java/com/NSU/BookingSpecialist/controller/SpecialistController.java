package com.NSU.BookingSpecialist.controller;

import com.NSU.BookingSpecialist.model.Appointment;
import com.NSU.BookingSpecialist.service.AppointmentService;
import com.NSU.BookingSpecialist.service.SpecialistService;
import com.NSU.BookingSpecialist.model.Specialist;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialists")
@RequiredArgsConstructor
public class SpecialistController {
    private final SpecialistService specialistService;
    private final AppointmentService appointmentService;

    @GetMapping
    public List<Specialist> getAllSpecialists() {
        return specialistService.getAll();
    }

    @GetMapping("/{id}")
    public Specialist getSpecialistById(@PathVariable Integer id) {
        return specialistService.getById(id);
    }

    @Data
    public static class CreateSpecialistRequest {
        @NotBlank String name;
        @NotBlank String profession;
    }

    @PostMapping
    public ResponseEntity<Specialist> create(@RequestBody CreateSpecialistRequest request) {
        Specialist specialist = specialistService.create(request.getName(), request.getProfession());
        return ResponseEntity.status(HttpStatus.CREATED).body(specialist);
    }

    @GetMapping("/{id}/appointments")
    public List<Appointment> getAppointmentsSpecialist(@PathVariable Integer id) {
        return appointmentService.getAppointmentsBySpecialist(id);
    }
}
