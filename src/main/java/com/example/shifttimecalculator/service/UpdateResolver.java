package com.example.shifttimecalculator.service;


import com.example.shifttimecalculator.constants.BotConstants;
import com.example.shifttimecalculator.exception.CantGetMessageException;
import com.example.shifttimecalculator.model.Conversation;
import com.example.shifttimecalculator.service.answer_resolver.CallBackResolver;
import com.example.shifttimecalculator.service.answer_resolver.ReplyResolver;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class UpdateResolver {
    private final HashMap<Long, Conversation> conversations;
    private final ReplyResolver replyResolver;
    private final CallBackResolver callBackResolver;
    private final StartConversation startConversation;
    private Conversation conversation;
    private Long chatId;

    public UpdateResolver(ReplyResolver replyResolver
            , CallBackResolver callBackResolver
            , StartConversation startConversation) {
        this.replyResolver = replyResolver;
        this.callBackResolver = callBackResolver;
        this.startConversation = startConversation;
        this.conversations = new HashMap<>();
    }

    public void resolve(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        getChatId(update);
        if (Objects.nonNull(chatId)) {
            if (Objects.isNull(callbackQuery)) {
                Message message = update.getMessage();
                if (this.isNewConversation(chatId)) {
                    createNewConversation(update);
                } else if (message.getText().equals(BotConstants.START_COMMAND)) {
                    resetConversation(chatId);
                    this.startConversation.startConversation(update, new Conversation());
                } else {
                    conversation = this.conversations.get(chatId);
                    this.replyResolver.handleRequest(update, conversation);
                }
            } else {
                Conversation conversation = this.conversations.get(chatId);
                this.callBackResolver.handleRequest(update, conversation);
            }

        }
    }

    private boolean isNewConversation(Long id) {
        return !this.conversations.containsKey(id);
    }

    private boolean resetConversation(Long chatId) {
        return Objects.nonNull(conversations.remove(chatId));
    }

    private void getChatId(Update update) {
        if (Objects.isNull(update.getCallbackQuery())
                && Objects.nonNull(update.getMessage())) {
            chatId = update.getMessage().getChatId();
        } else if (Objects.nonNull(update.getCallbackQuery()) && Objects.isNull(update.getMessage())) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
        } else {
            throw new CantGetMessageException(BotConstants.EXCEPTION_EMPTY_MESSAGE);
        }
    }

    private void createNewConversation(Update update) {
        conversation = new Conversation();
        this.conversations.put(chatId, conversation);
        this.startConversation.startConversation(update, conversation);
    }
}
