package com.example.shifttimecalculator.service.answer_as_reply;

import com.example.shifttimecalculator.model.Conversation;
import com.example.shifttimecalculator.service.calculation.CorrectStartShiftTime;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class ReplyToCorrectStartShiftTime {
    private final CorrectStartShiftTime correctStartShiftTime;

    public ReplyToCorrectStartShiftTime(CorrectStartShiftTime correctStartShiftTime) {
        this.correctStartShiftTime = correctStartShiftTime;
    }

    public void getCorrectStartShiftTime(Update update, Conversation conversation){
        correctStartShiftTime.getShiftWithCorrectedStartTime(update,conversation);
    }
}
