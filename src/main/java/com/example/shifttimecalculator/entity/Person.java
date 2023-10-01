package com.example.shifttimecalculator.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "persons", schema = "work_time_calculator")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String surname;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Sector> sectors;
}