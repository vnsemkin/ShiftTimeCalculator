package com.example.shifttimecalculator.dto;

import com.example.shifttimecalculator.constants.BotConstants;
import com.example.shifttimecalculator.entity.Person;
import com.example.shifttimecalculator.entity.Sector;
import com.example.shifttimecalculator.entity.Shift;
import com.example.shifttimecalculator.model.Conversation;
import lombok.Data;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class ConversationDTO {
    private Shift shift;
    private Sector sector;
    private List<Person> personList;

    public ConversationDTO(Conversation conversation) {
        this.shift = conversation.getShift();
        this.sector = conversation.getSector();
        this.personList = conversation.getPersonList();
    }

    public String getShiftInfo() {
        return toString();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (Objects.nonNull(shift)) {
            sb.append(new ShiftDTO(shift));
        }
        if (Objects.nonNull(sector)) {
            sb.append("\n");
            sb.append(new SectorDTO(sector));
        }
        if (Objects.nonNull(personList)) {
            sb.append("\n");
            sb.append(BotConstants.WORKER_LIST);
            sb.append("\n");
            sb.append(this.personList.stream().map((p) -> p.getName()
                            + " "
                            + p.getSurname())
                    .collect(Collectors.joining(" , " + "\n")));
        }
        return sb.toString();
    }
}
