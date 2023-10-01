package com.example.shifttimecalculator.entity;

import com.example.shifttimecalculator.model.DataPerPerson;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "shifts", schema = "work_time_calculator")
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private LocalDateTime start;
    @Column(unique = true)
    private LocalDateTime stop;
    @Transient
    private LocalDateTime correctedStartTime;
    @Transient
    private LocalDateTime correctedStopTime;
    @Transient
    private Duration duration;
    @Transient
    private DataPerPerson dataPerPerson;

}
