package com.example.shifttimecalculator.service.ask_question;


import com.example.shifttimecalculator.constants.BotConstants;
import com.example.shifttimecalculator.dto.SectorDTO;
import com.example.shifttimecalculator.dto.ShiftDTO;
import com.example.shifttimecalculator.entity.Sector;
import com.example.shifttimecalculator.model.Conversation;
import com.example.shifttimecalculator.repository.PersonRepo;
import com.example.shifttimecalculator.service.RespHandlerInterface;
import com.example.shifttimecalculator.util.BotKeyboardFactory;
import com.example.shifttimecalculator.util.MessageSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonQuestion implements RespHandlerInterface {
    private final MessageSender sender;
    private final PersonRepo personRepo;

    public PersonQuestion(MessageSender sender, PersonRepo personRepo) {
        this.sender = sender;
        this.personRepo = personRepo;
    }
    @Override
    public void handleRequest(Update update, Conversation conversation) {
        String sectorName = this.getSectorName(conversation.getSector());
        conversation.setStep(4);
        Message message = update.getCallbackQuery().getMessage();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        SendMessage sm = new SendMessage();
        ShiftDTO shiftDTO = new ShiftDTO(conversation.getShift());
        SectorDTO sectorDTO = new SectorDTO(conversation.getSector());
        this.sender.sendTextMessage(chatId, shiftDTO.toString());
        this.sender.sendTextMessage(chatId, sectorDTO.toString());
        sm.setChatId(message.getChatId());
        sm.setText(BotConstants.ADD_CONTROLLER);
        sm.setReplyMarkup(this.sendKeyboard(sectorName));
        this.sender.sendMessage(sm);
    }

    private InlineKeyboardMarkup sendKeyboard(String sector) {
        List<String> buttons = new ArrayList<>(this.personRepo
                .findBySectors_Name(sector)
                .stream()
                .map((p) -> p.getName() + " " + p.getSurname()).toList());
        buttons.add(BotConstants.GET_TIMETABLE);
        return BotKeyboardFactory.getInlineKeyboard(buttons, true);
    }

    private String getSectorName(Sector sector) {
        return Optional.ofNullable(sector.getName()).orElse("");
    }
}