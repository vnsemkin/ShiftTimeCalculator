package com.example.shifttimecalculator.service.ask_question;


import com.example.shifttimecalculator.constants.BotConstants;
import com.example.shifttimecalculator.dto.ShiftDTO;
import com.example.shifttimecalculator.model.Conversation;
import com.example.shifttimecalculator.service.RespHandlerInterface;
import com.example.shifttimecalculator.util.BotKeyboardFactory;
import com.example.shifttimecalculator.util.MessageSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
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
        Long chatId = Objects.isNull(update.getCallbackQuery())
                ? update.getMessage().getChatId() : update.getCallbackQuery()
                .getMessage()
                .getChatId();
        SendMessage sm = new SendMessage();
        sm.setChatId(chatId);
        sm.setText(BotConstants.LINE);
        sm.setReplyMarkup(this.sendKeyboard());
        ShiftDTO shiftDTO = new ShiftDTO(conversation.getShift());
        this.sender.sendTextMessage(chatId, shiftDTO.toString());
        this.sender.sendMessage(sm);
    }

    private InlineKeyboardMarkup sendKeyboard() {
        List<String> buttons = List.of(BotConstants.CORRECT_SHIFT_START
                , BotConstants.CORRECT_SHIFT_STOP
                , BotConstants.APPLY_CORRECTION);
        return BotKeyboardFactory.getInlineKeyboard(buttons, true);
    }
}
