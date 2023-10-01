package com.example.shifttimecalculator.repository;

import com.example.shifttimecalculator.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftRepo extends JpaRepository<Shift, Integer> {
    Shift findByName(String name);

}
