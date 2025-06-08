package com.NSU.BookingSpecialist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NSU.BookingSpecialist.model.Specialist;

@Repository
public interface SpecialistRepository extends JpaRepository<Specialist, Integer> {

}
