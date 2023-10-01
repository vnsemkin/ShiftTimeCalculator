package com.example.shifttimecalculator.model;

import com.example.shifttimecalculator.entity.Person;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class TimePeriod {
    private Duration duration;
    private LocalDateTime start;
    private LocalDateTime stop;
    private Person person;

}