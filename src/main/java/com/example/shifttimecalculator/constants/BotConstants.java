package com.example.shifttimecalculator.constants;

import java.time.Duration;

public class BotConstants {
    public static final String START_COMMAND = "/start";
    public static final String CUSTOM_START = "Начало";
    public static final String CUSTOM_STOP = "Конец";
    public static final String LINE = "-----------------------------------";
    public static final String GREETING = "<b>Привет: </b>";
    public static final String WORKER_LIST = "<b>Список работников: </b>";
    public static final String GET_TIMETABLE = "Дать Распределение";
    public static final String GET_SHIFTS = "<b>Выберите смену: </b>";
    public static final String GET_SECTOR = "<b>Выберите сектор: </b>";
    public static final String CORRECT_SHIFT_START = "Изменить начало смены";
    public static final String CORRECT_SHIFT_TIME = "<b>Укажите количество минут</b>";
    public static final String CORRECT_SHIFT_STOP = "Изменить конец смены";
    public static final String APPLY_CORRECTION = "Ничего не менять";
    public static final Duration MAX_WORK_PERIOD = Duration.ofMinutes(110L);
    public static final String UNABLE_TO_PARSE_TIME = "Не могу понять время!";
    public static final String TIME_BEFORE_SHIFT = "Некорректно! Время до начала смены!";
    public static final String TIME_AFTER_SHIFT = "Некорректно! Время за рамками смены!";
    public static final String NIGHT_SHIFT = "Ночная";
    public static final String WRONG_TIME = "Некорректное время";
    public static final String TIME_PATTERN = "'<b>Время: </b>'HH:mm";
    public static final String DATE_PATTERN = "'<b>Дата: </b>' dd  MMMM yyyy EEEE";
    public static final String ADD_CONTROLLER = "Добавьте работника: ";
    public static final String HTML_MARKUP = "HTML";
    public static final String USER_INPUT_TIME_FORMAT = "HH.mm; HH:mm; HH,mm; HH mm";
}
