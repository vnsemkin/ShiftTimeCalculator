package com.example.shifttimecalculator.service.question;


import com.example.shifttimecalculator.constants.BotConstants;
import com.example.shifttimecalculator.entity.Shift;
import com.example.shifttimecalculator.model.Conversation;
import com.example.shifttimecalculator.service.RespHandlerInterface;
import com.example.shifttimecalculator.service.dao.implementation.ShiftInterfaceImpl;
import com.example.shifttimecalculator.util.BotKeyboardFactory;
import com.example.shifttimecalculator.util.MessageSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@Service
public class ShiftQuestion implements RespHandlerInterface {
    private final MessageSender sender;
    private final ShiftInterfaceImpl shiftInterface;

    public ShiftQuestion(MessageSender sender, ShiftInterfaceImpl shiftInterface) {
        this.sender = sender;

        this.shiftInterface = shiftInterface;
    }

    public void handleRequest(Update update, Conversation conversation) {
        conversation.setStep(1);
        Message message = update.getMessage();
        SendMessage sm = new SendMessage();
        sm.setChatId(message.getChatId());
        sm.setText(BotConstants.CHOOSE_SHIFT);
        sm.setReplyMarkup(this.sendKeyboard());
        this.sender.sendMessage(sm);
    }

    private InlineKeyboardMarkup sendKeyboard() {
        List<String> buttons = shiftInterface.findAll()
                .stream()
                .map(Shift::getName).toList();
        return BotKeyboardFactory.getInlineKeyboard(buttons, false);
    }

}
