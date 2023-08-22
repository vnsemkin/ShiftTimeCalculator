package com.example.shifttimecalculator.service.callback_resolver;

import com.example.shifttimecalculator.dto.TimeTableDTO;
import com.example.shifttimecalculator.model.Conversation;
import com.example.shifttimecalculator.model.TimeTable;
import com.example.shifttimecalculator.service.RespHandlerInterface;

import com.example.shifttimecalculator.service.calc.TimeTableCalculation;
import com.example.shifttimecalculator.util.MessageSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class CallBackGetTimetable implements RespHandlerInterface {
    private final MessageSender sender;
    private final TimeTableCalculation timeTableCalculation;

    public CallBackGetTimetable(MessageSender sender, TimeTableCalculation timeTableCalculation) {
        this.sender = sender;
        this.timeTableCalculation = timeTableCalculation;
    }

    @Override
    public void handleRequest(Update update, Conversation conversation) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        TimeTable timeTable = this.timeTableCalculation.getTimeTable(conversation);
        TimeTableDTO timeTableDTO = new TimeTableDTO(timeTable);
        SendMessage sm = new SendMessage();
        sm.setChatId(chatId);
        sm.setText(timeTableDTO.toString());
        this.sender.sendMessage(sm);
    }
}
