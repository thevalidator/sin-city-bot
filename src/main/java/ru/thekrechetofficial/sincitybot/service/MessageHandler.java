/*
 * The Krechet Software
 */
package ru.thekrechetofficial.sincitybot.service;

import java.util.List;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 *
 * @author theValidator <the.validator@yandex.ru>
 */
public interface MessageHandler {
    
    List<SendMessage> textMessage(Update update);
    
    List<SendMessage> replyMessage(Update update);
    
    List<SendMessage> callBackDataMessage(Update update);
    
}
