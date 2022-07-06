/*
 * The Krechet Software
 */
package ru.thekrechetofficial.sincitybot.service;

import java.util.List;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 *
 * @author theValidator <the.validator@yandex.ru>
 */
public interface MessageHandler {
    
    List<BotApiMethod> textMessage(Update update);
    
    List<BotApiMethod> replyMessage(Update update);
    
    List<BotApiMethod> callBackDataMessage(Update update);
    
    AnswerCallbackQuery getAnswerCallbackQuery(String id, String text);
    
}
