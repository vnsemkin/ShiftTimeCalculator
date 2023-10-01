package com.example.shifttimecalculator.service.dao;

import com.example.shifttimecalculator.entity.Shift;

import java.util.List;

public interface ShiftInterface {
    List<Shift> findAll();

    Shift findByName(String name);

}
