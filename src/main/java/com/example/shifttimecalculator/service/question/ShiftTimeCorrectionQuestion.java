package com.example.shifttimecalculator.service.question;


import com.example.shifttimecalculator.model.Conversation;
import com.example.shifttimecalculator.service.RespHandlerInterface;
import com.example.shifttimecalculator.util.BotKeyboardFactory;
import com.example.shifttimecalculator.util.MessageSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.Objects;

@Service
public class ShiftTimeCorrectionQuestion implements RespHandlerInterface {
    private final MessageSender sender;

    public ShiftTimeCorrectionQuestion(MessageSender sender) {
        this.sender = sender;
    }

    public void handleRequest(Update update, Conversation conversation) {
        conversation.setStep(2);
        Long chatId = Objects.isNull(update.getCallbackQuery()) ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        SendMessage sm = new SendMessage();
        sm.setChatId(chatId);
        sm.setText("-----------------------------------");
        sm.setReplyMarkup(this.sendKeyboard());
        this.sender.sendMessage(sm);
    }

    private InlineKeyboardMarkup sendKeyboard() {
        List<String> buttons = List.of("Изменить начало смены"
                , "Изменить конец смены"
                , "Ничего не менять");
        return BotKeyboardFactory.getInlineKeyboard(buttons, true);
    }
}
