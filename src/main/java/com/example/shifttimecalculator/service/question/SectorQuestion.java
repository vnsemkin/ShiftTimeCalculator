package com.example.shifttimecalculator.service.question;


import com.example.shifttimecalculator.dto.ConversationDTO;
import com.example.shifttimecalculator.entity.Sector;
import com.example.shifttimecalculator.model.Conversation;
import com.example.shifttimecalculator.service.RespHandlerInterface;
import com.example.shifttimecalculator.service.dao.implementation.SectorInterfaceImpl;
import com.example.shifttimecalculator.util.BotKeyboardFactory;
import com.example.shifttimecalculator.util.MessageSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@Service
public class SectorQuestion implements RespHandlerInterface {
    private final MessageSender sender;
    private final SectorInterfaceImpl sectorInterface;

    public SectorQuestion(MessageSender sender, SectorInterfaceImpl sectorInterface) {
        this.sender = sender;
        this.sectorInterface = sectorInterface;
    }

    public void handleRequest(Update update, Conversation conversation) {
        conversation.setStep(3);
        Message message = update.getCallbackQuery().getMessage();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        SendMessage sm = new SendMessage();
        this.sender.sendTextMessage(chatId, new ConversationDTO(conversation).getShiftInfo());
        sm.setChatId(message.getChatId());
        sm.setText("<b>Выберите сектор: </b>");
        sm.setReplyMarkup(this.sendKeyboard());
        this.sender.sendMessage(sm);

    }

    private InlineKeyboardMarkup sendKeyboard() {
        List<String> buttons = sectorInterface.findAll().stream()
                .map(Sector::getName).toList();
        return BotKeyboardFactory.getInlineKeyboard(buttons, false);
    }
}