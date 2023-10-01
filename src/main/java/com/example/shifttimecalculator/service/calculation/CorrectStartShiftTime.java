package com.example.shifttimecalculator.service.calculation;


import com.example.shifttimecalculator.constants.BotConstants;
import com.example.shifttimecalculator.entity.Shift;
import com.example.shifttimecalculator.model.Conversation;
import com.example.shifttimecalculator.model.TimeValidator;
import com.example.shifttimecalculator.service.question.ShiftTimeCorrectionQuestion;
import com.example.shifttimecalculator.util.MessageSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Service
public class CorrectStartShiftTime {
    private final MessageSender sender;
    private final TimeValidatorService timeValidatorService;
    private final StartShiftCorrection startShiftCorrection;
    private final ShiftTimeCorrectionQuestion shiftTimeCorrectionQuestion;

    public CorrectStartShiftTime(MessageSender sender
            , TimeValidatorService timeValidatorService
            , StartShiftCorrection startShiftCorrection
            , ShiftTimeCorrectionQuestion shiftTimeCorrectionQuestion) {
        this.sender = sender;
        this.timeValidatorService = timeValidatorService;
        this.startShiftCorrection = startShiftCorrection;
        this.shiftTimeCorrectionQuestion = shiftTimeCorrectionQuestion;
    }

    public void getShiftWithCorrectedStartTime(Update update, Conversation conversation) {
        Long chatId = update.getMessage().getChatId();
        String userInput = update.getMessage().getText();
        Shift shift = conversation.getShift();
        Optional<LocalTime> usersLocalTime = this.parseStringToLocalTime(userInput);
        if (usersLocalTime.isPresent()) {
            TimeValidator timeValidator = timeValidatorService.validateShiftStartTime(shift
                    , usersLocalTime.get());
            if (timeValidator.isValidate()) {
                if (!timeValidator.isAfterMidnight()) {
                    Shift correctedShift = this.startShiftCorrection.correct(shift
                            , usersLocalTime.get());
                    conversation.setShift(correctedShift);
                    this.shiftTimeCorrectionQuestion.handleRequest(update, conversation);
                } else {
                    LocalDate localDate = shift.getStop().toLocalDate();
                    shift.setStart(localDate.atTime(shift.getStart().toLocalTime()));
                    Shift correctedShift = this.startShiftCorrection.correct(shift
                            , usersLocalTime.get());
                    conversation.setShift(correctedShift);
                    this.shiftTimeCorrectionQuestion.handleRequest(update, conversation);
                }
            } else {
                this.sender.sendTextMessage(chatId, timeValidator.getMessage());
                this.shiftTimeCorrectionQuestion.handleRequest(update, conversation);
            }
        } else {
            this.sender.sendTextMessage(chatId, BotConstants.UNABLE_TO_PARSE_TIME);
            this.shiftTimeCorrectionQuestion.handleRequest(update, conversation);
        }

    }

    private Optional<LocalTime> parseStringToLocalTime(String userInput) {
        String[] timeFormats = BotConstants.USER_INPUT_TIME_FORMAT.split(";\\s*");
        return this.tryParseLocalTime(userInput, timeFormats);
    }

    private Optional<LocalTime> tryParseLocalTime(String timeString, String[] formatPatterns) {
        return Arrays.stream(formatPatterns).map((formatPattern) -> {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
                return LocalTime.parse(timeString, formatter);
            } catch (DateTimeParseException e) {
                return null;
            }
        }).filter(Objects::nonNull).findFirst();
    }

}

