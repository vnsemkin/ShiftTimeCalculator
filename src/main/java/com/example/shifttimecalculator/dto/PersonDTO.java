package com.example.shifttimecalculator.dto;

import com.example.shifttimecalculator.entity.Person;
import lombok.Data;

@Data
public class PersonDTO {
    private final String name;
    private final String surname;

    public PersonDTO(Person person) {
        this.name = person.getName();
        this.surname = person.getSurname();
    }


    public String toString() {
        return "PersonDTO(name=" + name + ", surname=" + surname + ")";
    }
}
