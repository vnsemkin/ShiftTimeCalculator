package com.example.shifttimecalculator.service;

import com.example.shifttimecalculator.model.Conversation;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface RespHandlerInterface {
    void handleRequest(Update update, Conversation conversation);
}

