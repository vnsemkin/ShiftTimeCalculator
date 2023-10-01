package com.example.shifttimecalculator.service.question;

import com.example.shifttimecalculator.constants.BotConstants;
import com.example.shifttimecalculator.constants.Strategy;
import com.example.shifttimecalculator.dto.ConversationDTO;
import com.example.shifttimecalculator.model.Conversation;
import com.example.shifttimecalculator.service.RespHandlerInterface;
import com.example.shifttimecalculator.util.BotKeyboardFactory;
import com.example.shifttimecalculator.util.MessageSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Arrays;
import java.util.List;

@Service
public class StrategyQuestion implements RespHandlerInterface {
    private final MessageSender sender;

    public StrategyQuestion(MessageSender sender) {
        this.sender = sender;
    }

    @Override
    public void handleRequest(Update update, Conversation conversation) {
        conversation.setStep(5);
        Message message = update.getCallbackQuery().getMessage();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        SendMessage sm = new SendMessage();
        this.sender.sendTextMessage(chatId
                , new ConversationDTO(conversation).getShiftInfo());
        sm.setChatId(message.getChatId());
        sm.setText(BotConstants.CHOOSE_STRATEGY);
        sm.setReplyMarkup(sendKeyboard());
        this.sender.sendMessage(sm);
    }

    private InlineKeyboardMarkup sendKeyboard() {
        List<String> buttons = Arrays.stream(Strategy.values())
                .map(strategy -> {
                    if (strategy.equals(Strategy.EQUAL_PERIODS)) {
                        return BotConstants.STRATEGY_EQUAL_PERIODS;
                    } else {
                        return BotConstants.STRATEGY_ANOTHER;
                    }
                })
                .toList();
        return BotKeyboardFactory.getInlineKeyboard(buttons, true);
    }

}
