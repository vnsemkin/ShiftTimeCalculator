package com.example.shifttimecalculator.exception;

public class CantGetMessageException extends RuntimeException {
    public CantGetMessageException(String message) {
        super(message);
    }
}