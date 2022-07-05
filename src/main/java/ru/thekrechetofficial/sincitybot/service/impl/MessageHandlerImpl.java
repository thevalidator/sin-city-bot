/*
 * The Krechet Software
 */
package ru.thekrechetofficial.sincitybot.service.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.thekrechetofficial.sincitybot.bot.COMMAND;
import ru.thekrechetofficial.sincitybot.bot.InlineKeyboard;
import ru.thekrechetofficial.sincitybot.bot.MESSAGE;
import ru.thekrechetofficial.sincitybot.bot.ReplyKeyboard;
import ru.thekrechetofficial.sincitybot.entity.ad.GENDER;
import ru.thekrechetofficial.sincitybot.entity.ad.NLAd;
import ru.thekrechetofficial.sincitybot.service.MessageHandler;
import ru.thekrechetofficial.sincitybot.service.NLAdService;

/**
 * @author theValidator <the.validator@yandex.ru>
 */
@Service
public class MessageHandlerImpl implements MessageHandler {

    //@Value("${telegram.sheriff.id}")
    private String sheriffId;
    private final NLAdService service;

    @Autowired
    public MessageHandlerImpl(NLAdService service, @Value("${telegram.sheriff.id}") String sheriffId) {
        this.service = service;
    }
    
    

    @Override
    public List<BotApiMethod> textMessage(Update update) {

        String incomeMsg = update.getMessage().getText();
        String visitorId = String.valueOf(update.getMessage().getChatId());
        List<BotApiMethod> response = null;

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
            toVisitor.setReplyMarkup(InlineKeyboard.getGenderOptionForSearch());
            response = List.of(toVisitor);
        } else if (incomeMsg.equals(COMMAND.TARGET.getCommand())) {
//            SendMessage toVisitor = new SendMessage(visitorId, "Введи контактные данные");
//            //toVisitor.setReplyMarkup(ReplyKeyboard.getMainKeyboard());
//            response = List.of(toVisitor);
            
            List<NLAd> ads = service.getNewestByCreatorWithLimit(GENDER.FEMALE.toString(), 5);
            
            String msg = "TOTAL: " + ads.size() + "\n" + ads.get(0).toString();
            


            response = List.of(new SendMessage(String.valueOf(update.getMessage().getChatId()), msg));
        }

        if (response == null) {
            SendMessage toVisitor = new SendMessage(visitorId, MESSAGE.NO_ANSWER.getMsg());
            toVisitor.setReplyMarkup(ReplyKeyboard.getMainKeyboard());
            response = List.of(toVisitor);
            //response = List.of(new SendMessage(String.valueOf(update.getMessage().getChatId()), "UNDER CONSTRUCTION, SUKA BLYAT!"));
        }

        return response;

    }

    @Override
    public List<BotApiMethod> replyMessage(Update update) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<BotApiMethod> callBackDataMessage(Update update) {

        String option = update.getCallbackQuery().getData();
        String visitorId = String.valueOf(update.getCallbackQuery().getFrom().getId());// String.valueOf(update.getMessage().getChatId());
        String queryId = update.getCallbackQuery().getId();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        List<BotApiMethod> response = new ArrayList<>();

        if (option.matches("[MFCT]{1}")) {

            EditMessageText msg = new EditMessageText();
            msg.setChatId(visitorId);
            msg.setMessageId(messageId);
            msg.setText("Количество последних объявлений?");
            msg.setReplyMarkup(InlineKeyboard.getNumOptionForSearch(option));
            
            response.add(msg);
            
            
            //SendMessage toVisitor = new SendMessage(visitorId, "Количество последних объявлений?");
            //toVisitor.setReplyMarkup(InlineKeyboard.getNumOptionForSearch(option));
            //response.add(toVisitor);

        } else if (option.matches("[MFCT]{1} [0-9]{1,2}")) {
            
            EditMessageText msg = new EditMessageText();
            msg.setChatId(visitorId);
            msg.setMessageId(messageId);
            msg.setText("UNDER CONSTRUCTION, SUKA BLYAT!");

            response.add(msg);

//            SendMessage toVisitor = new SendMessage(visitorId, "Количество последних объявлений?");
//            toVisitor.setReplyMarkup(InlineKeyboard.getNumOptionForSearch(option));
//            response.add(toVisitor);
        }

        if (!response.isEmpty()) {
            response.add(getAnswerCallbackQuery(queryId));
            return response;
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static AnswerCallbackQuery getAnswerCallbackQuery(String id) {
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(id);
        return answer;
    }

}
