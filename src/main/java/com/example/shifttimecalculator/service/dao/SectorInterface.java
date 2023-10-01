package com.example.shifttimecalculator.service.dao;

import com.example.shifttimecalculator.entity.Sector;

import java.util.List;

public interface SectorInterface {
    List<Sector> findAll();

    Sector findByName(String name);

}
