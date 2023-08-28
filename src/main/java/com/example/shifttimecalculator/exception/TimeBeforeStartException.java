package com.example.shifttimecalculator.exception;

public class TimeBeforeStartException extends RuntimeException {
    public TimeBeforeStartException(String message) {
        super(message);
    }
}
