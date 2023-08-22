package com.example.shifttimecalculator.service.reply_resolver;

import com.example.shifttimecalculator.constants.BotConstants;
import com.example.shifttimecalculator.entity.Shift;
import com.example.shifttimecalculator.model.Conversation;
import com.example.shifttimecalculator.service.RespHandlerInterface;
import com.example.shifttimecalculator.service.StartConversation;
import com.example.shifttimecalculator.service.calc.CorrectStartShiftTime;
import com.example.shifttimecalculator.service.calc.StartShiftCorrection;
import com.example.shifttimecalculator.service.calc.StopShiftCorrection;
import com.example.shifttimecalculator.service.question.ShiftTimeCorrectionQuestion;
import com.example.shifttimecalculator.util.MessageSender;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.springframework.stereotype.Service;

@Service
public class ReplyResolver implements RespHandlerInterface {
    private final StartConversation startConversation;
    private final StartShiftCorrection startShiftCorrection;
    private final StopShiftCorrection stopShiftCorrection;
    private final MessageSender sender;
    private final ShiftTimeCorrectionQuestion shiftTimeCorrectionQuestion;
    private final CorrectStartShiftTime correctStartShiftTime;

    public ReplyResolver(MessageSender sender
            , StartConversation startConversation
            , StartShiftCorrection startShiftCorrection
            , StopShiftCorrection stopShiftCorrection
            , ShiftTimeCorrectionQuestion shiftTimeCorrectionQuestion
            , CorrectStartShiftTime correctStartShiftTime) {
        this.startConversation = startConversation;
        this.startShiftCorrection = startShiftCorrection;
        this.stopShiftCorrection = stopShiftCorrection;
        this.sender = sender;
        this.shiftTimeCorrectionQuestion = shiftTimeCorrectionQuestion;
        this.correctStartShiftTime = correctStartShiftTime;
    }

    public void handleRequest(Update update, Conversation conversation) {
        String answer = conversation.getAnswer();
        Shift shift = conversation.getShift();
        switch (answer) {
            case (BotConstants.CORRECT_SHIFT_START) ->
                    this.correctStartShiftTime
                            .getShiftWithCorrectedStartTime(update, conversation);
            case (BotConstants.CORRECT_SHIFT_STOP) ->
                    this.stopShiftCorrection.correct(shift, 10);
            default -> this.startConversation.startConversation(update, conversation);
        }
    }
}