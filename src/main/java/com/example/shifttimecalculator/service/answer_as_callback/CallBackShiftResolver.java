package com.example.shifttimecalculator.service.answer_as_callback;


import com.example.shifttimecalculator.entity.Shift;
import com.example.shifttimecalculator.model.Conversation;
import com.example.shifttimecalculator.service.RespHandlerInterface;
import com.example.shifttimecalculator.service.dao.implementation.ShiftInterfaceImpl;
import com.example.shifttimecalculator.service.question.ShiftTimeCorrectionQuestion;
import com.example.shifttimecalculator.util.ShiftUpdater;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class CallBackShiftResolver implements RespHandlerInterface {
    private final ShiftInterfaceImpl shiftInterface;
    private final ShiftTimeCorrectionQuestion shiftTimeCorrectionQuestion;

    public CallBackShiftResolver(ShiftInterfaceImpl shiftInterface
            , ShiftTimeCorrectionQuestion shiftTimeCorrectionQuestion) {
        this.shiftInterface = shiftInterface;
        this.shiftTimeCorrectionQuestion = shiftTimeCorrectionQuestion;
    }

    @Override
    public void handleRequest(Update update, Conversation conversation) {
        String data = update.getCallbackQuery().getData();
        Shift shift = shiftInterface.findByName(data);
        Shift newShift = ShiftUpdater.updateShiftDatesToCurrentTime(shift);
        conversation.setShift(newShift);
        this.shiftTimeCorrectionQuestion.handleRequest(update, conversation);
    }
}