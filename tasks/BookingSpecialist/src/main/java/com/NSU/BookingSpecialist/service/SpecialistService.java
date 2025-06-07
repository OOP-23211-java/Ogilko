package com.NSU.BookingSpecialist.service;

import com.NSU.BookingSpecialist.repository.SpecialistRepository;
import com.NSU.BookingSpecialist.model.Specialist;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialistService {
    private final SpecialistRepository specialistRepository;

    public List<Specialist> getAll() {
        return specialistRepository.findAll();
    }

    public Specialist getById(int id) {
        return specialistRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(""));
    }

}
