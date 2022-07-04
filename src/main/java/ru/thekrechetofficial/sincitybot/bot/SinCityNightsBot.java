/*
 * The Krechet Software
 */
package ru.thekrechetofficial.sincitybot.bot;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.thekrechetofficial.sincitybot.service.MessageHandler;

/**
 * @author theValidator <the.validator@yandex.ru>
 */
@Component
public class SinCityNightsBot extends TelegramLongPollingBot {

    @Value("${telegram.name}")
    private String botName;

    @Value("${telegram.token}")
    private String token;

    @Value("${telegram.chanel.id}")
    private String privateChannelId;

    private final MessageHandler messageHandler;

    @Autowired
    public SinCityNightsBot(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        
        List<SendMessage> messages = null;
        
        if (update.hasMessage()) {

            if (update.getMessage().isReply()) {
                messages = messageHandler.replyMessage(update);
            } else if (update.getMessage().hasText()) {
                messages = messageHandler.textMessage(update);
            } else {
                messages = List.of(new SendMessage(String.valueOf(update.getMessage().getChatId()), "UNDER CONSTRUCTION, WE'LL BE BACK SOON!"));
            }
            
        } else if (update.hasCallbackQuery()) {
            messages = messageHandler.callBackDataMessage(update);
        }
        
        executeAll(messages);
        
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    private void executeAll(List<SendMessage> messages) {

        try {
            
            for (SendMessage m : messages) {
                execute(m);
                //execute(this)
            }
            
        } catch (TelegramApiException ex) {
            Logger.getLogger(SinCityNightsBot.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}