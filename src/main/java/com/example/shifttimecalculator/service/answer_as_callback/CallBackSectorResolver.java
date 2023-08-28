package com.example.shifttimecalculator.service.answer_as_callback;


import com.example.shifttimecalculator.entity.Sector;
import com.example.shifttimecalculator.model.Conversation;
import com.example.shifttimecalculator.repository.SectorRepo;
import com.example.shifttimecalculator.service.RespHandlerInterface;
import com.example.shifttimecalculator.service.ask_question.PersonQuestion;
import com.example.shifttimecalculator.util.MessageSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class CallBackSectorResolver implements RespHandlerInterface {
    private final MessageSender sender;
    private final SectorRepo sectorRepo;
    private final PersonQuestion personQuestion;

    public CallBackSectorResolver(MessageSender sender, SectorRepo sectorRepo, PersonQuestion personQuestion) {
        this.sender = sender;
        this.sectorRepo = sectorRepo;
        this.personQuestion = personQuestion;
    }

    @Override
    public void handleRequest(Update update, Conversation conversation) {
        String data = update.getCallbackQuery().getData();
//        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        Sector sector = this.sectorRepo.findByName(data);
//        ShiftDTO shiftDTO = new ShiftDTO(conversation.getShift());
//        SectorDTO sectorDTO = new SectorDTO(sector);
//        this.sender.sendTextMessage(chatId, shiftDTO.toString());
//        this.sender.sendTextMessage(chatId, sectorDTO.toString());
        conversation.setSector(sector);
        this.personQuestion.handleRequest(update, conversation);
    }
}

