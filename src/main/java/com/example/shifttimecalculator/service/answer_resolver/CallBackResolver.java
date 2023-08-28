package com.example.shifttimecalculator.service.answer_resolver;


import com.example.shifttimecalculator.model.Conversation;
import com.example.shifttimecalculator.service.RespHandlerInterface;
import com.example.shifttimecalculator.service.answer_as_callback.CallBackPersonResolver;
import com.example.shifttimecalculator.service.answer_as_callback.CallBackSectorResolver;
import com.example.shifttimecalculator.service.answer_as_callback.CallBackShiftResolver;
import com.example.shifttimecalculator.service.answer_as_callback.CallBackShiftTimeResolver;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.springframework.stereotype.Service;

@Service
public class CallBackResolver implements RespHandlerInterface {
    private final CallBackShiftResolver shiftResolver;
    private final CallBackSectorResolver sectorResolver;
    private final CallBackPersonResolver personResolver;
    private final CallBackShiftTimeResolver callBackShiftTimeResolver;

    public CallBackResolver(CallBackShiftResolver shiftResolver, CallBackSectorResolver sectorResolver, CallBackPersonResolver personResolver, CallBackShiftTimeResolver callBackShiftTimeResolver) {
        this.shiftResolver = shiftResolver;
        this.sectorResolver = sectorResolver;
        this.personResolver = personResolver;
        this.callBackShiftTimeResolver = callBackShiftTimeResolver;
    }

    public void handleRequest(Update update, Conversation conversation) {
        Integer step = conversation.getStep();
        conversation.setAnswer(update.getCallbackQuery().getData());
        switch (step) {
            case 1 -> this.shiftResolver.handleRequest(update, conversation);
            case 2 -> this.callBackShiftTimeResolver.handleRequest(update, conversation);
            case 3 -> this.sectorResolver.handleRequest(update, conversation);
            case 4 -> this.personResolver.handleRequest(update, conversation);
        }

    }
}

