package com.example.shifttimecalculator.util;

import com.example.shifttimecalculator.constants.BotConstants;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class MessageSender extends DefaultAbsSender {
    public MessageSender(Environment env) {
        super(new DefaultBotOptions()
                , env.getProperty("telegram.token"));
    }

    public void sendTextMessage(Long chatId, String text) {
        SendMessage sm = new SendMessage();
        sm.setText(text);
        sm.setChatId(chatId);
        sm.setParseMode(BotConstants.HTML_MARKUP);

        try {
            this.execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(SendMessage sm) {
        try {
            sm.setParseMode(BotConstants.HTML_MARKUP);
            this.execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
