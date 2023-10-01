package com.example.shifttimecalculator.service.calculation;

import com.example.shifttimecalculator.entity.Shift;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class StopShiftCorrection {
    public StopShiftCorrection() {
    }

    public Shift correct(Shift shift, int minutes) {
        LocalDateTime newStop = shift.getStop().minusMinutes(minutes);
        shift.setCorrectedStopTime(newStop);
        return shift;
    }

}

