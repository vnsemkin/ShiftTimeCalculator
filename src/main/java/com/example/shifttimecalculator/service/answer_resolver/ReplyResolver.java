package com.example.shifttimecalculator.service.answer_resolver;

import com.example.shifttimecalculator.constants.BotConstants;
import com.example.shifttimecalculator.model.Conversation;
import com.example.shifttimecalculator.service.RespHandlerInterface;
import com.example.shifttimecalculator.service.StartConversation;
import com.example.shifttimecalculator.service.answer_as_reply.ReplyToCorrectStartShiftTime;
import com.example.shifttimecalculator.service.answer_as_reply.ReplyToCorrectStopTime;
import com.example.shifttimecalculator.service.calculation.StopShiftCorrection;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

@Service
public class ReplyResolver implements RespHandlerInterface {
    private final StartConversation startConversation;
    private final StopShiftCorrection stopShiftCorrection;
    private final ReplyToCorrectStartShiftTime replyToCorrectStartShiftTime;
    private final ReplyToCorrectStopTime replyToCorrectStopTime;

    public ReplyResolver(StartConversation startConversation
            , StopShiftCorrection stopShiftCorrection
            , ReplyToCorrectStartShiftTime replyToCorrectStartShiftTime, ReplyToCorrectStopTime replyToCorrectStopTime) {
        this.startConversation = startConversation;
        this.stopShiftCorrection = stopShiftCorrection;
        this.replyToCorrectStartShiftTime = replyToCorrectStartShiftTime;
        this.replyToCorrectStopTime = replyToCorrectStopTime;
    }

    public void handleRequest(Update update, Conversation conversation) {
        if (Objects.nonNull(conversation.getAnswer())) {
            String answer = conversation.getAnswer();
            switch (answer) {
                case (BotConstants.CORRECT_SHIFT_START) ->
                        this.replyToCorrectStartShiftTime.getCorrectStartShiftTime(update
                                , conversation);
                case (BotConstants.CORRECT_SHIFT_STOP) -> this.replyToCorrectStopTime
                        .getStopShiftCorrection(update, conversation);
                default -> this.startConversation.startConversation(update, conversation);
            }
        } else {
            this.startConversation.startConversation(update, conversation);
        }
    }
}