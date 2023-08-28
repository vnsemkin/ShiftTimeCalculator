package com.example.shifttimecalculator.repository;

import com.example.shifttimecalculator.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PersonRepo extends JpaRepository<Person, Integer> {
    Set<Person> findBySectors_Name(String sectorName);
    Person findByNameAndSurname(String name, String surname);
}