package com.example.shifttimecalculator.service.answer_as_reply;

import com.example.shifttimecalculator.constants.BotConstants;
import com.example.shifttimecalculator.entity.Shift;
import com.example.shifttimecalculator.model.Conversation;
import com.example.shifttimecalculator.service.question.ShiftTimeCorrectionQuestion;
import com.example.shifttimecalculator.service.calculation.StopShiftCorrection;
import com.example.shifttimecalculator.util.MessageSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class ReplyToCorrectStopTime {
    private final StopShiftCorrection stopShiftCorrection;
    private final MessageSender sender;
    private final ShiftTimeCorrectionQuestion shiftTimeCorrectionQuestion;

    public ReplyToCorrectStopTime(StopShiftCorrection stopShiftCorrection
            , MessageSender sender, ShiftTimeCorrectionQuestion shiftTimeCorrectionQuestion) {
        this.stopShiftCorrection = stopShiftCorrection;
        this.sender = sender;
        this.shiftTimeCorrectionQuestion = shiftTimeCorrectionQuestion;
    }

    public void getStopShiftCorrection(Update update, Conversation conversation) {
        Long chatId = update.getMessage().getChatId();
        int userInput = 0;
        try {
            userInput = Integer.parseInt(update.getMessage().getText());
        } catch (Exception e) {
            sender.sendTextMessage(chatId, BotConstants.UNABLE_TO_PARSE_TIME);
        }
        if (validate(chatId, userInput)) {
            Shift correctedShift = stopShiftCorrection.correct(conversation.getShift(), userInput);
            conversation.setShift(correctedShift);
            this.shiftTimeCorrectionQuestion.handleRequest(update, conversation);
        } else {
            this.shiftTimeCorrectionQuestion.handleRequest(update, conversation);

        }
    }

    private boolean validate(Long chatId, Integer minutes) {
        if (minutes > 1 && minutes < 30) {
            return true;
        } else {
            sender.sendTextMessage(chatId, BotConstants.WRONG_MINUTES);
            return false;
        }
    }

}
