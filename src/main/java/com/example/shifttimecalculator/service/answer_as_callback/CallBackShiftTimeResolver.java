package com.example.shifttimecalculator.service.answer_as_callback;


import com.example.shifttimecalculator.constants.BotConstants;
import com.example.shifttimecalculator.model.Conversation;
import com.example.shifttimecalculator.service.RespHandlerInterface;
import com.example.shifttimecalculator.service.question.SectorQuestion;
import com.example.shifttimecalculator.util.MessageSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class CallBackShiftTimeResolver implements RespHandlerInterface {
    private final MessageSender sender;
    private final SectorQuestion sectorQuestion;

    public CallBackShiftTimeResolver(MessageSender sender, SectorQuestion sectorQuestion) {
        this.sectorQuestion = sectorQuestion;
        this.sender = sender;
    }

    @Override
    public void handleRequest(Update update, Conversation conversation) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String data = update.getCallbackQuery().getData();
        SendMessage sm;
        switch (data) {
            case (BotConstants.CORRECT_SHIFT_START) -> {
                sm = new SendMessage();
                sm.setChatId(chatId);
                sm.setText("<b>Введите время начала смены</b>\nПример 14.25");
                this.sender.sendMessage(sm);
            }
            case (BotConstants.CORRECT_SHIFT_STOP) -> {
                sm = new SendMessage();
                sm.setChatId(chatId);
                sm.setText("<b>Введите кол-во минут</b>\nПример 10");
                this.sender.sendMessage(sm);
            }
            default -> this.sectorQuestion.handleRequest(update, conversation);
        }
    }
}

