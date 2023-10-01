package com.example.shifttimecalculator.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sectors", schema = "work_time_calculator")
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;
}