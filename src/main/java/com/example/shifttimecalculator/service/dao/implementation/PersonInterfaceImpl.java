package com.example.shifttimecalculator.service.dao.implementation;

import com.example.shifttimecalculator.entity.Person;
import com.example.shifttimecalculator.repository.PersonRepo;
import com.example.shifttimecalculator.service.dao.PersonInterface;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PersonInterfaceImpl implements PersonInterface {
    private final PersonRepo personRepo;

    public PersonInterfaceImpl(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    public Set<Person> findBySector(String name) {
        return personRepo.findBySectors_Name(name);
    }

    @Override
    public Person findByNameAndSurname(String name, String surname) {
        return personRepo.findByNameAndSurname(name, surname);
    }

}