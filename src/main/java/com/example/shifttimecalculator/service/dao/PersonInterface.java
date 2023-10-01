package com.example.shifttimecalculator.service.dao;

import com.example.shifttimecalculator.entity.Person;

import java.util.Set;

public interface PersonInterface {
    Set<Person> findBySector(String sectorName);

    Person findByNameAndSurname(String name, String surname);
}
