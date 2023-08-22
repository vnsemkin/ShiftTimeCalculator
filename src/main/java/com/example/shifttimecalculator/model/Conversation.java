package com.example.shifttimecalculator.model;

import com.example.shifttimecalculator.entity.Person;
import com.example.shifttimecalculator.entity.Sector;
import com.example.shifttimecalculator.entity.Shift;
import lombok.Data;

import java.util.List;

@Data
public class Conversation {
    private String answer;
    private Integer step;
    private Shift shift;
    private Sector sector;
    private List<Person> personList;
}
