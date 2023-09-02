package com.example.shifttimecalculator.controller;


import com.example.shifttimecalculator.service.UpdateResolver;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Controller
public class TelegramBotController extends TelegramLongPollingBot {
    private final UpdateResolver updateResolver;

    public TelegramBotController(Environment env, UpdateResolver updateResolver) {
        super(env.getProperty("telegram.token"));
        this.updateResolver = updateResolver;
    }

    @Override
    public void onUpdateReceived(Update update) {
        this.updateResolver.resolve(update);
    }

    public String getBotUsername() {
        return "Time Table Calculator";
    }
}