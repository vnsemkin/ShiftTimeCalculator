package com.example.shifttimecalculator.service.answer_as_callback;


import com.example.shifttimecalculator.constants.BotConstants;
import com.example.shifttimecalculator.entity.Person;
import com.example.shifttimecalculator.model.Conversation;
import com.example.shifttimecalculator.repository.PersonRepo;
import com.example.shifttimecalculator.service.RespHandlerInterface;
import com.example.shifttimecalculator.service.question.PersonQuestion;
import com.example.shifttimecalculator.service.question.StrategyQuestion;
import com.example.shifttimecalculator.util.MessageSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class CallBackPersonResolver implements RespHandlerInterface {
    private final PersonRepo personRepo;
    private final StrategyQuestion strategyQuestion;
    private final Set<Person> personSet;
    private final PersonQuestion personQuestion;
    private final MessageSender sender;

    public CallBackPersonResolver(StrategyQuestion strategyQuestion
            , PersonRepo personRepo
            , PersonQuestion personQuestion, MessageSender sender) {
        this.personRepo = personRepo;
        this.strategyQuestion = strategyQuestion;
        this.personQuestion = personQuestion;
        this.sender = sender;
        this.personSet = new HashSet<>();
    }

    @Override
    public void handleRequest(Update update, Conversation conversation) {
        String data = update.getCallbackQuery().getData();
        if (!Objects.equals(data, BotConstants.CONTINUE)) {
            Person person = this.getPersonFromString(data);
            this.personSet.add(person);
            //Add persons to PersonsList in Conversation
            conversation.setPersonList(this.personSet.stream().toList());
            this.personQuestion.handleRequest(update, conversation);
        } else {
            if (Objects.isNull(conversation.getPersonList()) || conversation.getPersonList().size() < 2) {
                Long chatId = update.getCallbackQuery().getMessage().getChatId();
                sender.sendTextMessage(chatId, BotConstants.NOT_ENOUGH_WORKERS);
                this.personQuestion.handleRequest(update, conversation);
            } else {
                this.strategyQuestion.handleRequest(update, conversation);
            }
        }
    }

    private Person getPersonFromString(String data) {
        String[] s = data.split(" ");
        if (s.length != 0) {
            String name = s[0];
            String surname = s[1];
            return this.personRepo.findByNameAndSurname(name, surname);
        } else {
            return new Person();
        }
    }

}

