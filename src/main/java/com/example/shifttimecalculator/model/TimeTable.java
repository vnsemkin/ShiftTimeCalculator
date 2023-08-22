package com.example.shifttimecalculator.model;

import com.example.shifttimecalculator.entity.Shift;
import lombok.Data;

import java.util.List;

@Data
public class TimeTable {
    private Shift shift;
    private List<TimePeriod> timePeriodList;
}