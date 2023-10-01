package com.example.shifttimecalculator.service.calculation;


import com.example.shifttimecalculator.constants.BotConstants;
import com.example.shifttimecalculator.entity.Person;
import com.example.shifttimecalculator.entity.Shift;
import com.example.shifttimecalculator.model.Conversation;
import com.example.shifttimecalculator.model.DataPerPerson;
import com.example.shifttimecalculator.model.TimePeriod;
import com.example.shifttimecalculator.model.TimeTable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimeTableCalculation {
    private Duration shiftDuration;
    private final long oneTimeWorkingPeriod;

    public TimeTableCalculation() {
        this.oneTimeWorkingPeriod
                = BotConstants.MAX_WORK_PERIOD.toMinutes();
    }

    public TimeTable getTimeTable(Conversation conversation) {
        Shift shift = conversation.getShift();
        LocalDateTime start = shift.getStart();
        LocalDateTime stop = shift.getStop();
        this.shiftDuration = Duration.between(start, stop);
        shift.setDuration(this.shiftDuration);
        shift.setDataPerPerson(this.getDataPerPerson(conversation));
        TimeTable timeTable = new TimeTable();
        timeTable.setShift(shift);
        timeTable.setTimePeriodList(this.setPersonToTimePeriodList(conversation));
        return timeTable;
    }

    private List<TimePeriod> setPersonToTimePeriodList(Conversation conversation) {
        List<TimePeriod> timePeriodList = this.getTimePeriodsForShift(conversation);
        List<Person> personList = conversation.getPersonList();

        for (int i = 0; i < timePeriodList.size(); ++i) {
            (timePeriodList.get(i)).setPerson(personList.get(i % personList.size()));
        }

        return timePeriodList;
    }

    public DataPerPerson getDataPerPerson(Conversation conversation) {
        List<Person> personSet = conversation.getPersonList();
        Duration overallDurationPerPerson = this.shiftDuration.dividedBy(personSet.size());
        long overallDurationPerPersonMinutes = overallDurationPerPerson.toMinutes();
        if (overallDurationPerPerson.toMinutes() > this.oneTimeWorkingPeriod) {
            long numberOfWorkPeriodsPerPerson = this.getNumberOfWorkPeriodsPerPerson(overallDurationPerPersonMinutes);
            return new DataPerPerson(overallDurationPerPersonMinutes, this.oneTimeWorkingPeriod, numberOfWorkPeriodsPerPerson);
        } else {
            return new DataPerPerson(overallDurationPerPersonMinutes, this.oneTimeWorkingPeriod, 1L);
        }
    }

    private long getNumberOfWorkPeriodsPerPerson(long overallDurationPerPersonMinutes) {
        long numberOfWorkPeriodsPerPerson = overallDurationPerPersonMinutes / this.oneTimeWorkingPeriod;
        numberOfWorkPeriodsPerPerson = numberOfWorkPeriodsPerPerson == 0L ? 1L : numberOfWorkPeriodsPerPerson;
        long remainTime = overallDurationPerPersonMinutes - numberOfWorkPeriodsPerPerson * this.oneTimeWorkingPeriod;
        return remainTime < 0L ? 0L : numberOfWorkPeriodsPerPerson + 1L;
    }

    private List<TimePeriod> getTimePeriodsForShift(Conversation conversation) {
        return this.getEqualPeriods(conversation);
    }

    private List<TimePeriod> getEqualPeriods(Conversation conversation) {
        List<TimePeriod> timePeriods = new ArrayList<>();
        Shift shift = conversation.getShift();
        DataPerPerson dataPerPerson = shift.getDataPerPerson();
        long overallTimePerPeriod = dataPerPerson.overallTime();
        int personsNumber = conversation.getPersonList().size();
        int periodsNumberForPerson = (int) conversation.getShift().getDataPerPerson().periodsNumber();
        int periodsDuringShift = personsNumber * periodsNumberForPerson;
        Duration periodDuration = Duration.ofMinutes(overallTimePerPeriod / (long) periodsNumberForPerson);
        Duration remainder = Duration.ofMinutes(overallTimePerPeriod % (long) periodsNumberForPerson);
        LocalDateTime start = shift.getStart();
        int lastPeriods = periodsDuringShift - (personsNumber + periodsNumberForPerson - 1);

        while (periodsDuringShift != 0) {
            TimePeriod timePeriod = new TimePeriod();
            if (remainder.toMinutes() == 0L) {
                timePeriod.setStart(start);
                timePeriod.setStop(start.plusMinutes(periodDuration.toMinutes()));
                timePeriod.setDuration(periodDuration);
                start = timePeriod.getStop();
                timePeriods.add(timePeriod);
                --periodsDuringShift;
            } else {
                timePeriod.setStart(start);
                if (periodsDuringShift > lastPeriods) {
                    timePeriod.setStop(start.plusMinutes(periodDuration.toMinutes()));
                    timePeriod.setDuration(periodDuration);
                } else {
                    timePeriod.setStop(start.plusMinutes(periodDuration.plus(remainder).toMinutes()));
                    timePeriod.setDuration(periodDuration.plus(remainder));
                }

                start = timePeriod.getStop();
                timePeriods.add(timePeriod);
                --periodsDuringShift;
            }
        }

        return timePeriods;
    }
}

