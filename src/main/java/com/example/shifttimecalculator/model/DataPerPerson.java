package com.example.shifttimecalculator.model;

import lombok.Data;

@Data
public class DataPerPerson {
    private final long overallTime;
    private final long referencePeriod;
    private final long periodsNumber;

    public DataPerPerson(long overallTime, long referencePeriod, long periodsNumber) {
        this.overallTime = overallTime;
        this.referencePeriod = referencePeriod;
        this.periodsNumber = periodsNumber;
    }
}
