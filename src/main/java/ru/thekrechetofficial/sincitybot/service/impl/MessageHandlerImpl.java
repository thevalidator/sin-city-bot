/*
 * The Krechet Software
 */
package ru.thekrechetofficial.sincitybot.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.thekrechetofficial.sincitybot.bot.COMMAND;
import ru.thekrechetofficial.sincitybot.bot.InlineKeyboard;
import ru.thekrechetofficial.sincitybot.bot.MESSAGE;
import ru.thekrechetofficial.sincitybot.bot.ReplyKeyboard;
import ru.thekrechetofficial.sincitybot.service.MessageHandler;

/**
 * @author theValidator <the.validator@yandex.ru>
 */
@Service
public class MessageHandlerImpl implements MessageHandler {

    @Value("${telegram.sheriff.id}")
    private String sheriffId;

    @Override
    public List<SendMessage> textMessage(Update update) {

        String incomeMsg = update.getMessage().getText();
        String visitorId = String.valueOf(update.getMessage().getChatId());
        List<SendMessage> response = null;

        if (incomeMsg.startsWith("/")) {

            if (incomeMsg.equals(COMMAND.START.getCommand())) {

                User user = update.getMessage().getFrom();//.getMessage().getContact();
                String visitor = String.format("𝕾𝖍𝖊𝖗𝖎𝖋𝖋, 𝖓𝖊𝖜 𝖛𝖎𝖘𝖎𝖙𝖔𝖗 𝖎𝖓 𝖙𝖍𝖊 𝖈𝖎𝖙𝖞!\n\nɪᴅ: %d\nɴᴀᴍᴇ: %s\n"
                        + "ᴜꜱᴇʀɴᴀᴍᴇ: %s\nʟᴀɴɢᴜᴀɢᴇ: %s\n",
                        user.getId(),
                        user.getFirstName() + " " + user.getLastName(),
                        user.getUserName(),
                        user.getLanguageCode());

                SendMessage toVisitor = new SendMessage(visitorId, MESSAGE.GREETENG.getMsg());
                toVisitor.setReplyMarkup(ReplyKeyboard.getMainKeyboard());
                SendMessage toSheriff = new SendMessage(sheriffId, visitor);

                response = List.of(toSheriff, toVisitor);

            }

        } else if (incomeMsg.equals(COMMAND.HELP.getCommand())) {
            response = List.of(new SendMessage(visitorId, MESSAGE.HELP.getMsg()));
        } else if (incomeMsg.equals(COMMAND.ACCOUNT.getCommand())) {
            response = List.of(new SendMessage(String.valueOf(update.getMessage().getChatId()), "UNDER CONSTRUCTION, WE'LL BE BACK SOON!"));
        } else if (incomeMsg.equals(COMMAND.SEARCH.getCommand())) {
            SendMessage toVisitor = new SendMessage(visitorId, "Выбери опцию");
            toVisitor.setReplyMarkup(ReplyKeyboard.getSearchKeyboard());
            response = List.of(toVisitor);
        } else if (incomeMsg.equals(COMMAND.BACK.getCommand())) {
            SendMessage toVisitor = new SendMessage(visitorId, "Главное меню");
            toVisitor.setReplyMarkup(ReplyKeyboard.getMainKeyboard());
            response = List.of(toVisitor);
        } else if (incomeMsg.equals(COMMAND.SCOUT.getCommand())) {
            SendMessage toVisitor = new SendMessage(visitorId, "Кого ищем?");
            //toVisitor.setReplyMarkup(ReplyKeyboard.getMainKeyboard());
            response = List.of(toVisitor);
        } else if (incomeMsg.equals(COMMAND.TARGET.getCommand())) {
            SendMessage toVisitor = new SendMessage(visitorId, "Введи контактные данные");
            //toVisitor.setReplyMarkup(ReplyKeyboard.getMainKeyboard());
            response = List.of(toVisitor);
        }

        if (response == null) {
            SendMessage toVisitor = new SendMessage(visitorId, MESSAGE.NO_ANSWER.getMsg());
            toVisitor.setReplyMarkup(ReplyKeyboard.getMainKeyboard());
            response = List.of(toVisitor);
            //response = List.of(new SendMessage(String.valueOf(update.getMessage().getChatId()), "UNDER CONSTRUCTION, WE'LL BE BACK SOON!"));
        }

        return response;

    }

    @Override
    public List<SendMessage> replyMessage(Update update) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<SendMessage> callBackDataMessage(Update update) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
