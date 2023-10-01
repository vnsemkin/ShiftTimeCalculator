package com.example.shifttimecalculator.service.calculation;


import com.example.shifttimecalculator.constants.BotConstants;
import com.example.shifttimecalculator.entity.Shift;
import com.example.shifttimecalculator.model.TimeValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class TimeValidatorService {
    public TimeValidator validateShiftStartTime(Shift shift, LocalTime newTime) {
        TimeValidator timeValidator = new TimeValidator();
        if (!shift.getName().equalsIgnoreCase(BotConstants.NIGHT_SHIFT)) {
            if (newTime.isBefore(shift.getStart().toLocalTime())) {
                timeValidator.setValidate(false);
                timeValidator.setMessage(BotConstants.TIME_BEFORE_SHIFT);
            } else if (newTime.isAfter(shift.getStop().toLocalTime())) {
                timeValidator.setValidate(false);
                timeValidator.setMessage(BotConstants.TIME_AFTER_SHIFT);
            } else {
                timeValidator.setValidate(true);
            }
        } else {
            LocalDateTime shiftStartTime = shift.getStart();
            LocalDateTime shiftStopTime = shift.getStop();
            if (newTime.isAfter(LocalTime.of(0, 0))
                    && newTime.isBefore(shiftStopTime.toLocalTime())) {
                timeValidator.setValidate(true);
                timeValidator.setAfterMidnight(true);
            } else if (newTime.isAfter(shiftStartTime.toLocalTime())
                    && newTime.isBefore(LocalTime.of(23, 59))) {
                timeValidator.setValidate(true);
            } else {
                timeValidator.setValidate(false);
                timeValidator.setMessage(BotConstants.WRONG_TIME);
            }
        }
        return timeValidator;
    }

}
