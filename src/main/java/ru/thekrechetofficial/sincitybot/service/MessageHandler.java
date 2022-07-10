/*
 * The Krechet Software
 */
package ru.thekrechetofficial.sincitybot.service;

import java.io.Serializable;
import java.util.List;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 *
 * @author theValidator <the.validator@yandex.ru>
 */
public interface MessageHandler {
    
    List<BotApiMethod<? extends Serializable>> textMessage(Update update);
    
    List<PartialBotApiMethod<? extends Serializable>> callBackDataMessage(Update update);
    
    AnswerCallbackQuery getAnswerCallbackQuery(String id, String text);
    
}
