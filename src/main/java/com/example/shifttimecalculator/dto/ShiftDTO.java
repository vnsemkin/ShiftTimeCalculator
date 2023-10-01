package com.example.shifttimecalculator.dto;

import com.example.shifttimecalculator.constants.BotConstants;
import com.example.shifttimecalculator.entity.Shift;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
public class ShiftDTO {
    private final String name;
    private final LocalDateTime start;
    private final LocalDateTime stop;

    public ShiftDTO(Shift shift) {
        this.name = shift.getName();
        this.start = shift.getCorrectedStartTime();
        this.stop = shift.getCorrectedStopTime();
    }

    public String toString() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(BotConstants.TIME_PATTERN);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(BotConstants.DATE_PATTERN
                , new Locale("ru", "RU"));
        String formattedStartTime = this.start.format(timeFormatter);
        String formattedStartDate = this.start.format(dateFormatter);
        String formattedStopTime = this.stop.format(timeFormatter);
        String formattedStopDate = this.stop.format(dateFormatter);
        return "<b>Смена: </b>"
                + "\n"
                + this.name
                + "\n<b>Начало смены: </b>"
                + "\n" + formattedStartTime
                + "\n" + formattedStartDate
                + "\n<b>Окончание смены: </b>"
                + "\n" + formattedStopTime
                + "\n" + formattedStopDate;
    }
}