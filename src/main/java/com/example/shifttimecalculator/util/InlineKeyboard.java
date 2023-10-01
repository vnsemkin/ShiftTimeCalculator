package com.example.shifttimecalculator.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class InlineKeyboard {
    public InlineKeyboard() {
    }

    public static InlineKeyboardMarkup getInlineKeyboard(List<String> buttons, boolean rows) {
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(keyboardRows(buttons, rows));
        return keyboard;
    }

    private static List<List<InlineKeyboardButton>> keyboardRows(List<String> buttons
            , boolean rows) {
        return rows ? buttons.stream().map(List::of).map(InlineKeyboard::setButtons).toList() : List.of(setButtons(buttons));
    }

    private static List<InlineKeyboardButton> setButtons(List<String> buttons) {
        return buttons.stream().map((button) -> {
            InlineKeyboardButton customButton = new InlineKeyboardButton();
            customButton.setText(button);
            customButton.setCallbackData(button);
            return customButton;
        }).toList();
    }
}
