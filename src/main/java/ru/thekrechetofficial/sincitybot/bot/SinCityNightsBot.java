/*
 * The Krechet Software
 */
package ru.thekrechetofficial.sincitybot.bot;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
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

        if (update.hasMessage()) {

            if (update.getMessage().hasText()) {
                executeAll(messageHandler.textMessage(update));
            } else {
                executeOne(new SendMessage(String.valueOf(update.getMessage().getChatId()), MESSAGE.NO_ANSWER.getMsg()));
            }

        } else if (update.hasCallbackQuery()) {
            
            String id = update.getCallbackQuery().getId();
            executeOne(messageHandler.getAnswerCallbackQuery(id, "Обрабатывается"));
            executeAllAbstract(messageHandler.callBackDataMessage(update));
            
        }

    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    private void executeOne(BotApiMethod message) {
        try {
            execute(message);
        } catch (TelegramApiException ex) {
            Logger.getLogger(SinCityNightsBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void executeAll(List<BotApiMethod<? extends Serializable>> messages) {
        try {
            for (BotApiMethod m : messages) {
                execute(m);
            }
        } catch (TelegramApiException ex) {
            Logger.getLogger(SinCityNightsBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void executeAllAbstract(List<PartialBotApiMethod<? extends Serializable>> messages) {
        try {
            for (PartialBotApiMethod response : messages) {
                if (response instanceof EditMessageText) {
                    execute((EditMessageText) response);
                } else if (response instanceof SendDocument) {
                    execute((SendDocument) response);
                }
            }
        } catch (TelegramApiException ex) {
            Logger.getLogger(SinCityNightsBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
