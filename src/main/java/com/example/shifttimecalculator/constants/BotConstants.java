package com.example.shifttimecalculator.constants;

import java.time.Duration;

public class BotConstants {
    public static final String START_COMMAND = "/start";
    public static final String GREETING = "<b>Привет: </b>";
    public static final String WORKER_LIST = "<b>Список работников: </b>";
    public static final String CORRECT_SHIFT_START = "Изменить начало смены";
    public static final String CORRECT_SHIFT_STOP = "Изменить конец смены";
    public static final String CONTINUE = "Продолжить";
    public static final Duration MAX_WORK_PERIOD = Duration.ofMinutes(110L);
    public static final String UNABLE_TO_PARSE_TIME = "Не могу понять время!";
    public static final String TIME_BEFORE_SHIFT = "Некорректно! Время до начала смены!";
    public static final String TIME_AFTER_SHIFT = "Некорректно! Время за рамками смены!";
    public static final String NIGHT_SHIFT = "Ночная";
    public static final String WRONG_TIME = "Некорректное время";
    public static final String WRONG_MINUTES = "Неправильное количество минут";
    public static final String TIME_PATTERN = "HH:mm";
    public static final String DATE_PATTERN = "dd  MMMM yyyy";
    public static final String ADD_CONTROLLER = "<b>Добавьте работника: </b>";
    public static final String HTML_MARKUP = "HTML";
    public static final String USER_INPUT_TIME_FORMAT = "HH.mm; HH:mm; HH,mm; HH mm";
    public static final String SECTOR = "<b>Рабочий сектор: </b>";
    public static final String CHOOSE_SHIFT = "<b>Выберите смену: </b>";
    public static final String CORRECT_TIME = "<b>Скорректируйте время: </b>";
    public static final String CHOOSE_STRATEGY = "<b>Выберите стратегию: </b>";
    public static final String NOT_ENOUGH_WORKERS = "<b>Добавьте работников</b>";
    public static final String STRATEGY_EQUAL_PERIODS = "Равные промежутки";
    public static final String STRATEGY_ANOTHER = "Другая";
    public static final String EXCEPTION_EMPTY_MESSAGE = "Пустой update";
}
