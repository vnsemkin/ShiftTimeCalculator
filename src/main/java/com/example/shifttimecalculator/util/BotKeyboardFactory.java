package com.example.shifttimecalculator.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

public class BotKeyboardFactory {

    public static InlineKeyboardMarkup getInlineKeyboard(List<String> buttons, boolean rows) {
        return InlineKeyboard.getInlineKeyboard(buttons, rows);
    }
}
