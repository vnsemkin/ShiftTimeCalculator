package com.example.shifttimecalculator.service.answer_as_callback;

import com.example.shifttimecalculator.constants.BotConstants;
import com.example.shifttimecalculator.constants.Strategy;
import com.example.shifttimecalculator.model.Conversation;
import com.example.shifttimecalculator.service.RespHandlerInterface;
import com.example.shifttimecalculator.service.question.StrategyQuestion;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

@Service
public class CallBackStrategyResolver implements RespHandlerInterface {
    private final CallBackGetTimetable callBackGetTimetable;
    private final StrategyQuestion strategyQuestion;

    public CallBackStrategyResolver(CallBackGetTimetable callBackGetTimetable, StrategyQuestion strategyQuestion) {
        this.callBackGetTimetable = callBackGetTimetable;
        this.strategyQuestion = strategyQuestion;
    }


    @Override
    public void handleRequest(Update update, Conversation conversation) {
        if (Objects.nonNull(update.getCallbackQuery().getData())) {
            String strategy = update.getCallbackQuery().getData();
            if (Objects.equals(strategy, BotConstants.STRATEGY_EQUAL_PERIODS)) {
                conversation.setStrategy(Strategy.EQUAL_PERIODS);
                callBackGetTimetable.handleRequest(update, conversation);
            } else {
                strategyQuestion.handleRequest(update, conversation);
            }
        }
    }

}
