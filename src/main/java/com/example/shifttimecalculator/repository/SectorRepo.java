package com.example.shifttimecalculator.repository;

import com.example.shifttimecalculator.entity.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectorRepo extends JpaRepository<Sector, Integer> {
    Sector findByName(String name);
}
