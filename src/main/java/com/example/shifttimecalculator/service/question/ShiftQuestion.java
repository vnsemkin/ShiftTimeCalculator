package com.example.shifttimecalculator.service.question;


import com.example.shifttimecalculator.entity.Shift;
import com.example.shifttimecalculator.model.Conversation;
import com.example.shifttimecalculator.repository.ShiftRepo;
import com.example.shifttimecalculator.service.RespHandlerInterface;
import com.example.shifttimecalculator.util.BotKeyboardFactory;
import com.example.shifttimecalculator.util.MessageSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@Service
public class ShiftQuestion implements RespHandlerInterface {
    private final MessageSender sender;
    private final ShiftRepo shiftRepo;

    public ShiftQuestion(MessageSender sender, ShiftRepo shiftRepo) {
        this.sender = sender;
        this.shiftRepo = shiftRepo;
    }

    public void handleRequest(Update update, Conversation conversation) {
        conversation.setStep(1);
        Message message = update.getMessage();
        SendMessage sm = new SendMessage();
        sm.setChatId(message.getChatId());
        sm.setText("<b>Выберите смену: </b>");
        sm.setReplyMarkup(this.sendKeyboard());
        this.sender.sendMessage(sm);
    }

    private InlineKeyboardMarkup sendKeyboard() {
        List<String> buttons = this.shiftRepo.findAll()
                .stream()
                .map(Shift::getName).toList();
        return BotKeyboardFactory.getInlineKeyboard(buttons, false);
    }
}
