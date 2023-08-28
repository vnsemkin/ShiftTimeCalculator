package com.example.shifttimecalculator.service;

import com.example.shifttimecalculator.constants.BotConstants;
import com.example.shifttimecalculator.model.Conversation;
import com.example.shifttimecalculator.service.ask_question.ShiftQuestion;
import com.example.shifttimecalculator.util.MessageSender;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.springframework.stereotype.Service;

@Service
public class StartConversation {
    private final MessageSender sender;
    private final ShiftQuestion shiftQuestion;

    public StartConversation(MessageSender sender, ShiftQuestion shiftQuestion) {
        this.sender = sender;
        this.shiftQuestion = shiftQuestion;
    }

    public void startConversation(Update update, Conversation conversation) {
        conversation.setStep(0);
        String firstName = update.getMessage().getChat().getFirstName();
        Long chatId = update.getMessage().getChatId();
        String text = BotConstants.GREETING + firstName;
        this.sender.sendTextMessage(chatId, text);
        this.shiftQuestion.handleRequest(update, conversation);
    }
}

