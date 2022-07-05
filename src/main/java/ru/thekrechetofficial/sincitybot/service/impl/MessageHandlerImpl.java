/*
 * The Krechet Software
 */
package ru.thekrechetofficial.sincitybot.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import ru.thekrechetofficial.sincitybot.entity.SUBSCRIPTION_TYPE;
import ru.thekrechetofficial.sincitybot.entity.ScoutQuery;
import ru.thekrechetofficial.sincitybot.entity.Subscription;
import ru.thekrechetofficial.sincitybot.entity.Visitor;
import ru.thekrechetofficial.sincitybot.entity.ad.GENDER;
import ru.thekrechetofficial.sincitybot.entity.ad.NLAd;
import ru.thekrechetofficial.sincitybot.service.MessageHandler;
import ru.thekrechetofficial.sincitybot.service.NLAdService;
import ru.thekrechetofficial.sincitybot.service.VisitorService;

/**
 * @author theValidator <the.validator@yandex.ru>
 */
@Service
public class MessageHandlerImpl implements MessageHandler {

    @Value("${telegram.sheriff.id}")
    private String sheriffId;
    private final NLAdService nlService;
    private final VisitorService visitorService;

    @Autowired
    public MessageHandlerImpl(NLAdService nlService, VisitorService visitorService, @Value("${telegram.sheriff.id}") String sheriffId) {
        this.nlService = nlService;
        this.visitorService = visitorService;
    }

    @Override
    public List<BotApiMethod> textMessage(Update update) {

        String incomeMsg = update.getMessage().getText();
        String visitorId = String.valueOf(update.getMessage().getChatId());
        List<BotApiMethod> response = new ArrayList<>();

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
                response.add(toVisitor);

                if (!visitorService.isExistByTelegramId(visitorId)) {
                    SendMessage toSheriff = new SendMessage(sheriffId, visitor);
                    response.add(toSheriff);
                    Visitor v = new Visitor(visitorId, LocalDateTime.now(), new Subscription(SUBSCRIPTION_TYPE.STANDARD), new ScoutQuery());
                    visitorService.saveVisitor(v);
                }

            }
            
        } else if (incomeMsg.equals(COMMAND.HELP.getCommand())) {
            SendMessage toVisitor = new SendMessage(visitorId, MESSAGE.HELP.getMsg());
            //toVisitor.setReplyMarkup(ReplyKeyboard.getMainKeyboard());                          //TODO: probably not needed
            response.add(toVisitor);
        } else if (incomeMsg.equals(COMMAND.ACCOUNT.getCommand())) {
            SendMessage toVisitor = new SendMessage(visitorId, "UNDER CONSTRUCTION, SUKA BLYAT!");
            //toVisitor.setReplyMarkup(ReplyKeyboard.getMainKeyboard());                          //TODO: probably not needed
            response.add(toVisitor);
        } else if (incomeMsg.equals(COMMAND.SEARCH.getCommand())) {
            SendMessage toVisitor = new SendMessage(visitorId, "Выбери опцию ⤵️");
            toVisitor.setReplyMarkup(ReplyKeyboard.getSearchKeyboard());
            response.add(toVisitor);
        } else if (incomeMsg.equals(COMMAND.BACK.getCommand())) {
            SendMessage toVisitor = new SendMessage(visitorId, "Главное меню");
            toVisitor.setReplyMarkup(ReplyKeyboard.getMainKeyboard());
            response.add(toVisitor);
        } else if (incomeMsg.equals(COMMAND.SCOUT.getCommand())) {
            SendMessage toVisitor = new SendMessage(visitorId, "Кого ищем?");
            toVisitor.setReplyMarkup(InlineKeyboard.getGenderOptionForSearch());
            response.add(toVisitor);
        } else if (incomeMsg.equals(COMMAND.TARGET.getCommand())) {
//            SendMessage toVisitor = new SendMessage(visitorId, "Введи контактные данные");
//            //toVisitor.setReplyMarkup(ReplyKeyboard.getMainKeyboard());
//            response = List.of(toVisitor);


            SendMessage toVisitor = new SendMessage(visitorId, "UNDER CONSTRUCTION, SUKA BLYAT!");
            //toVisitor.setReplyMarkup(ReplyKeyboard.getSearchKeyboard());                        //TODO: probably not needed
            response.add(toVisitor);

        }

        if (response.isEmpty()) {
            SendMessage toVisitor = new SendMessage(visitorId, MESSAGE.NO_ANSWER.getMsg());
            toVisitor.setReplyMarkup(ReplyKeyboard.getMainKeyboard());
            response.add(toVisitor);
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
            
        } else if (option.matches("[MFCT]{1} [0-9]{1,2}")) {

            EditMessageText msg = new EditMessageText();
            msg.setChatId(visitorId);
            msg.setMessageId(messageId);
            msg.setText("UNDER CONSTRUCTION, SUKA BLYAT!");

            response.add(msg);
            
        }

        if (!response.isEmpty()) {
            response.add(getAnswerCallbackQuery(queryId));
            return response;
        }

        throw new IllegalArgumentException("Wrong data: " + option);
    }

    private static AnswerCallbackQuery getAnswerCallbackQuery(String id) {
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(id);
        return answer;
    }

}
