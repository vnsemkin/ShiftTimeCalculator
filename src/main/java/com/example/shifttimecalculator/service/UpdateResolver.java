package com.example.shifttimecalculator.service;


import com.example.shifttimecalculator.constants.BotConstants;
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
        if (Objects.isNull(callbackQuery)) {
            Message message = update.getMessage();
            Long chatId = update.getMessage().getChatId();
            Conversation conversation;
            if (this.isNewConversation(chatId)) {
                conversation = new Conversation();
                this.conversations.put(chatId, conversation);
                this.startConversation.startConversation(update, conversation);
            } else if (message.getText().equals(BotConstants.START_COMMAND)) {
                conversation = this.conversations.get(message.getChatId());
                this.startConversation.startConversation(update, conversation);
            } else {
                conversation = this.conversations.get(chatId);
                this.replyResolver.handleRequest(update, conversation);
            }
        } else {
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            Conversation conversation = this.conversations.get(chatId);
            this.callBackResolver.handleRequest(update, conversation);
        }

    }

    private boolean isNewConversation(Long id) {
        return !this.conversations.containsKey(id);
    }
}
