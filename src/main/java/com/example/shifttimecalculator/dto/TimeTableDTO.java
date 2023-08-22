package com.example.shifttimecalculator.dto;


import com.example.shifttimecalculator.model.TimePeriod;
import com.example.shifttimecalculator.model.TimeTable;
import lombok.Data;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TimeTableDTO {
    private final String shiftName;
    private final LocalTime startShift;
    private final LocalTime stopShift;
    private final Duration shiftDuration;
    private final Duration durationPerPerson;
    private final List<TimePeriod> timePeriodList;

    public TimeTableDTO(TimeTable timeTable) {
        this.shiftName = timeTable.getShift().getName();
        this.startShift = LocalTime.from(timeTable.getShift().getStart());
        this.stopShift = LocalTime.from(timeTable.getShift().getStop());
        this.shiftDuration = timeTable.getShift().getDuration();
        this.timePeriodList = timeTable.getTimePeriodList();
        this.durationPerPerson = Duration.ofMinutes(timeTable.getShift().getDataPerPerson().getOverallTime());
    }

    public String toString() {
        String collect = this.timePeriodList.stream().map((p) -> {
            String person = p.getPerson().getName();
            return "Работник: " + person
                    + " "
                    + p.getPerson().getSurname()
                    + "\nДлительность: "
                    + p.getDuration()
                    + "\nПринимает: "
                    + p.getStart()
                    + "\nМеняют: "
                    + p.getStop()
                    + "\n";
        }).collect(Collectors.joining("\n"));
        return "Распределение:\nСмена: "
                + this.shiftName
                + "\nДлительность смены: "
                + this.shiftDuration
                + "\nНачало смены: "
                + this.startShift
                + "\nКонец смены: "
                + this.stopShift
                + "\nДлительность дежурства работника: "
                + this.durationPerPerson
                + "\n\n"
                + collect;
    }
}
