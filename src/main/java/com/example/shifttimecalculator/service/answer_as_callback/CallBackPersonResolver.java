package com.example.shifttimecalculator.service.answer_as_callback;


import com.example.shifttimecalculator.constants.BotConstants;
import com.example.shifttimecalculator.dto.PersonDTO;
import com.example.shifttimecalculator.entity.Person;
import com.example.shifttimecalculator.model.Conversation;
import com.example.shifttimecalculator.repository.PersonRepo;
import com.example.shifttimecalculator.service.RespHandlerInterface;
import com.example.shifttimecalculator.service.ask_question.PersonQuestion;
import com.example.shifttimecalculator.util.MessageSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CallBackPersonResolver implements RespHandlerInterface {
    private final MessageSender sender;
    private final PersonRepo personRepo;
    private final Set<PersonDTO> personDTOSet;
    private final Set<Person> personSet;
    private final PersonQuestion personQuestion;
    private final CallBackGetTimetable callBackGetTimetable;

    public CallBackPersonResolver(MessageSender sender
            , PersonRepo personRepo
            , PersonQuestion personQuestion
            , CallBackGetTimetable callBackGetTimetable) {
        this.sender = sender;
        this.personRepo = personRepo;
        this.personQuestion = personQuestion;
        this.callBackGetTimetable = callBackGetTimetable;
        this.personSet = new HashSet<>();
        this.personDTOSet = new HashSet<>();
    }

    @Override
    public void handleRequest(Update update, Conversation conversation) {
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        if (!Objects.equals(data, BotConstants.GET_TIMETABLE)) {
            Person person = this.getPersonFromString(data);
            PersonDTO personDTO = new PersonDTO(person);
            this.personDTOSet.add(personDTO);
            this.personSet.add(person);
            String concatenatedNames = this.personSet.stream().map((p) -> p.getName()
                            + " "
                            + p.getSurname())
                    .collect(Collectors.joining(" , "));
            conversation.setPersonList(this.personSet.stream().toList());
            this.sender.sendTextMessage(chatId, "<b>Список работников: </b>");
            this.sender.sendTextMessage(chatId, concatenatedNames);
            this.personQuestion.handleRequest(update, conversation);
        } else {
            this.callBackGetTimetable.handleRequest(update, conversation);
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

