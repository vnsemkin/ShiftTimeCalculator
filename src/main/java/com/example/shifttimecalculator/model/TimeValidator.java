package com.example.shifttimecalculator.model;

import lombok.Data;

@Data
public class TimeValidator {
    private String message;
    private boolean validate;
    private boolean afterMidnight;
}
