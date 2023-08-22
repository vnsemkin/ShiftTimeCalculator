package com.example.shifttimecalculator.service.calc;

import com.example.shifttimecalculator.entity.Shift;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class StartShiftCorrection {
    public StartShiftCorrection() {
    }

    public Shift correct(Shift shift, LocalTime newShiftStartFromUser) {
        LocalDateTime start = shift.getStart();
        LocalDateTime correctedDateTime = start.with(LocalTime.from(newShiftStartFromUser));
        shift.setStart(LocalDateTime.from(correctedDateTime));
        return shift;
    }
}

