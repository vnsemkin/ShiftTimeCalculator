package com.example.shifttimecalculator.service.answer_as_callback;


import com.example.shifttimecalculator.entity.Shift;
import com.example.shifttimecalculator.model.Conversation;
import com.example.shifttimecalculator.repository.ShiftRepo;
import com.example.shifttimecalculator.service.RespHandlerInterface;
import com.example.shifttimecalculator.service.ask_question.ShiftTimeCorrectionQuestion;
import com.example.shifttimecalculator.util.MessageSender;
import com.example.shifttimecalculator.util.ShiftUpdater;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class CallBackShiftResolver implements RespHandlerInterface {
    private final MessageSender sender;
    private final ShiftRepo shiftRepo;
    private final ShiftTimeCorrectionQuestion shiftTimeCorrectionQuestion;

    public CallBackShiftResolver(MessageSender sender
            , ShiftRepo shiftRepo
            , ShiftTimeCorrectionQuestion shiftTimeCorrectionQuestion) {
        this.sender = sender;
        this.shiftRepo = shiftRepo;
        this.shiftTimeCorrectionQuestion = shiftTimeCorrectionQuestion;
    }

    @Override
    public void handleRequest(Update update, Conversation conversation) {
        String data = update.getCallbackQuery().getData();
//        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        Shift shift = this.shiftRepo.findByName(data);
        Shift newShift = ShiftUpdater.updateShiftDatesToCurrentTime(shift);
//        ShiftDTO shiftDTO = new ShiftDTO(newShift);
//        this.sender.sendTextMessage(chatId, shiftDTO.toString());
        conversation.setShift(newShift);
        this.shiftTimeCorrectionQuestion.handleRequest(update, conversation);
    }
}

