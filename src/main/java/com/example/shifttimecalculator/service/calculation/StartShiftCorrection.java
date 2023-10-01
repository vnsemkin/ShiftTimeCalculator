package com.example.shifttimecalculator.service.calculation;

import com.example.shifttimecalculator.entity.Shift;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class StartShiftCorrection {
    public Shift correct(Shift shift, LocalTime newShiftStartFromUser) {
        LocalDateTime initialShiftStart = shift.getStart();
        LocalDateTime correctedDateTime = initialShiftStart.with(LocalTime.from(newShiftStartFromUser));
        shift.setCorrectedStartTime(LocalDateTime.from(correctedDateTime));
        return shift;
    }

}

