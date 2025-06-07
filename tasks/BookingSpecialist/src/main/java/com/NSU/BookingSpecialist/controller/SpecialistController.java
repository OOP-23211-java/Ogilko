package com.NSU.BookingSpecialist.controller;

import com.NSU.BookingSpecialist.service.SpecialistService;
import com.NSU.BookingSpecialist.model.Specialist;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/specialists")
@RequiredArgsConstructor
public class SpecialistController {
    private final SpecialistService specialistService;

    @GetMapping
    public List<Specialist> getAllSpecialists() {
        return specialistService.getAll();
    }

    @GetMapping("/{id}")
    public Specialist getSpecialistById(@PathVariable Integer id) {
        return specialistService.getById(id);
    }
}
