package com.example.shifttimecalculator.service.answer_as_callback;


import com.example.shifttimecalculator.entity.Sector;
import com.example.shifttimecalculator.model.Conversation;
import com.example.shifttimecalculator.repository.SectorRepo;
import com.example.shifttimecalculator.service.RespHandlerInterface;
import com.example.shifttimecalculator.service.question.PersonQuestion;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class CallBackSectorResolver implements RespHandlerInterface {
    private final SectorRepo sectorRepo;
    private final PersonQuestion personQuestion;

    public CallBackSectorResolver(SectorRepo sectorRepo
            , PersonQuestion personQuestion) {
        this.sectorRepo = sectorRepo;
        this.personQuestion = personQuestion;
    }

    @Override
    public void handleRequest(Update update, Conversation conversation) {
        String data = update.getCallbackQuery().getData();
        Sector sector = this.sectorRepo.findByName(data);
        conversation.setSector(sector);
        this.personQuestion.handleRequest(update, conversation);
    }
}