package com.example.shifttimecalculator.util;

import com.example.shifttimecalculator.constants.BotConstants;
import com.example.shifttimecalculator.entity.Shift;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ShiftUpdater {
    public ShiftUpdater() {
    }

    public static Shift updateShiftDatesToCurrentTime(Shift originalShift) {
        LocalTime startTime = originalShift.getStart().toLocalTime();
        LocalTime stopTime = originalShift.getStop().toLocalTime();
        LocalDateTime currentDate = LocalDateTime.now().withNano(0);
        LocalDateTime updatedStartTime = currentDate.with(startTime);
        LocalDateTime updatedStopTime;
        if (!originalShift.getName().equalsIgnoreCase(BotConstants.NIGHT_SHIFT)) {
            updatedStopTime = currentDate.with(stopTime);
        } else {
            updatedStopTime = currentDate.plusDays(1L).with(stopTime);
        }

        Shift updatedShift = new Shift();
        updatedShift.setId(originalShift.getId());
        updatedShift.setName(originalShift.getName());
        updatedShift.setStart(updatedStartTime);
        updatedShift.setCorrectedStartTime(updatedStartTime);
        updatedShift.setStop(updatedStopTime);
        updatedShift.setCorrectedStopTime(updatedStopTime);
        updatedShift.setDuration(originalShift.getDuration());
        updatedShift.setDataPerPerson(originalShift.getDataPerPerson());
        return updatedShift;
    }
}
