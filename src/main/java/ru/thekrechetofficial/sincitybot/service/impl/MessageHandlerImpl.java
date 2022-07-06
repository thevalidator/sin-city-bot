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
import ru.thekrechetofficial.sincitybot.bot.keyboard.InlineKeyboard;
import ru.thekrechetofficial.sincitybot.bot.MESSAGE;
import ru.thekrechetofficial.sincitybot.bot.keyboard.ReplyKeyboard;
import ru.thekrechetofficial.sincitybot.entity.SUBSCRIPTION_TYPE;
import ru.thekrechetofficial.sincitybot.entity.ScoutQuery;
import ru.thekrechetofficial.sincitybot.entity.Subscription;
import ru.thekrechetofficial.sincitybot.entity.Visitor;
import ru.thekrechetofficial.sincitybot.entity.ad.Gender;
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
        String visitorId = String.valueOf(update.getMessage().getFrom().getId());
        List<BotApiMethod> response = new ArrayList<>();

        if (incomeMsg.startsWith("/")) {
            if (incomeMsg.equals(COMMAND.START.getCommand())) {

                User user = update.getMessage().getFrom();
                String newVisitorInformMsg = String.format("𝕾𝖍𝖊𝖗𝖎𝖋𝖋, 𝖓𝖊𝖜 𝖛𝖎𝖘𝖎𝖙𝖔𝖗 𝖎𝖓 𝖙𝖍𝖊 𝖈𝖎𝖙𝖞!\n\nɪᴅ: %d\nɴᴀᴍᴇ: %s\n"
                        + "ᴜꜱᴇʀɴᴀᴍᴇ: %s\nʟᴀɴɢᴜᴀɢᴇ: %s\n",
                        user.getId(),
                        user.getFirstName() + " " + user.getLastName(),
                        user.getUserName(),
                        user.getLanguageCode());

                SendMessage toVisitor = new SendMessage(visitorId, MESSAGE.GREETENG.getMsg());
                toVisitor.setReplyMarkup(ReplyKeyboard.getMainKeyboard());
                response.add(toVisitor);

                if (!visitorService.isExistByTelegramId(visitorId)) {
                    SendMessage toSheriff = new SendMessage(sheriffId, newVisitorInformMsg);
                    response.add(toSheriff);
                    Visitor v = createVisitor(visitorId);
                    visitorService.saveVisitor(v);
                }

            }

        } else if (incomeMsg.equals(COMMAND.HELP.getCommand())) {
            SendMessage toVisitor = new SendMessage(visitorId, MESSAGE.HELP.getMsg());
            //toVisitor.setReplyMarkup(ReplyKeyboard.getMainKeyboard());                          //TODO: probably not needed
            response.add(toVisitor);
        } else if (incomeMsg.equals(COMMAND.ACCOUNT.getCommand())) {
            Subscription s = visitorService.getVisitorsSubscription(visitorId);
            String message = String.format("КАБИНЕТ\n\n🔹 Тип подписки: %s\n🔹 Осталось запросов: %d\n\n", s.getType().getValue(), s.getRequests());
            SendMessage toVisitor = new SendMessage(visitorId, message);
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

            SendMessage toVisitor = new SendMessage(visitorId, "Введи контактные данные");
            toVisitor.setReplyMarkup(ReplyKeyboard.getReplyOnRequestKeyboard());
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

        String query = update.getMessage().getText().trim();
        List<BotApiMethod> response = new ArrayList<>();
        String visitorId = String.valueOf(update.getMessage().getFrom().getId());

        Subscription s = visitorService.getVisitorsSubscription(visitorId);
        int requestsLeft = s.getRequests();
        if (requestsLeft > 0 || s.getType().equals(SUBSCRIPTION_TYPE.PREMIUM)) {

            if (query.matches("[a-zA-Z0-9\\.!'@#$%&*()+\\/=?^_`{|}~\\s-]{4,40}")) {

                long count = nlService.getAdsCountByContact("%" + query + "%");
                SendMessage msg = new SendMessage(visitorId, "По данному контакту найдено " + count + " объявлений.");
                msg.setReplyMarkup(ReplyKeyboard.getSearchKeyboard());
                response.add(msg);
                
                if (s.getType().equals(SUBSCRIPTION_TYPE.STANDARD)) {
                    visitorService.updateRequests(requestsLeft - 1, visitorId);
                }

                //return getSearchResultMsg(DBConnector.getConnection(), update, sb, query);
            } else {

                SendMessage msg = new SendMessage(visitorId, "Неверный формат введенных данных. Возможно,"
                        + " вы ввели слишком короткий или длинный текст.");
                msg.setReplyMarkup(ReplyKeyboard.getSearchKeyboard());
                response.add(msg);
//            logger.error("[USR] {} [SEARCH] {} (WRONG QUERY)",
//                    update.getMessage().getFrom().getId(), query);
//            return SendMsgBuilder.sendTextMessage(update, "Неверный формат введенных данных. Возможно,"
//                    + " вы ввели слишком короткий текст. Ознакомьтесь с инструкцией по использованию.",
//                    Keyboard.getMainKeyboard());
            }

        } else {
            SendMessage msg = new SendMessage(visitorId, "ПОИСК НЕВОЗМОЖЕН\n\nВы истратили все свои запросы");
            msg.setReplyMarkup(ReplyKeyboard.getSearchKeyboard());
            response.add(msg);
        }

        return response;

    }

    @Override
    public List<BotApiMethod> callBackDataMessage(Update update) {

        String option = update.getCallbackQuery().getData();
        String visitorId = String.valueOf(update.getCallbackQuery().getFrom().getId());
        //String queryId = update.getCallbackQuery().getId();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        List<BotApiMethod> response = new ArrayList<>();
        //response.add(getAnswerCallbackQuery(queryId));

        if (option.matches("^[MFCT]{1}$")) {

            EditMessageText msg = new EditMessageText();
            msg.setChatId(visitorId);
            msg.setMessageId(messageId);
            msg.setText("Количество последних объявлений?");
            msg.setReplyMarkup(InlineKeyboard.getNumOptionForSearch(option));

            response.add(msg);

        } else if (option.matches("^[MFCT]{1} [0-9]{1,2}$")) {

            String[] data = option.split(" ");

            List<String> offerIds = nlService.getNewestOfferIdByCreatorWithLimit(
                    Gender.fromString(data[0]).toString(),
                    Integer.valueOf(data[1]));

            Visitor visitor = visitorService.getOptionalFullVisitorByTelegramId(visitorId);

            String timestamp = String.valueOf(System.currentTimeMillis());
            visitor.updateQueryData(offerIds, timestamp);
            //visitor.getScoutQuery().createQueryOffers(offerIds);
            //visitor.getScoutQuery().setQueryStamp(timestamp);
            visitorService.saveVisitor(visitor);

            NLAd ad = nlService.getAdById(offerIds.get(0));

            EditMessageText msg = new EditMessageText();
            msg.setChatId(visitorId);
            msg.setMessageId(messageId);
            msg.setText(ad.toString());
            msg.setReplyMarkup(InlineKeyboard.getAdsView(1, offerIds.size(), timestamp));

            response.add(msg);

        } else if (option.matches("^[0-9]{1,2}-[0-9]{1,2}-[0-9]{1,}$")) {

            String[] data = option.split("-");

            int page = Integer.valueOf(data[0]);
            int pagesTotal = Integer.valueOf(data[1]);

            EditMessageText msg = new EditMessageText();
            msg.setChatId(visitorId);
            msg.setMessageId(messageId);

            //String actualQueryStamp = visitorService.getVisitorsQueryStamp(visitorId);
            Visitor visitor = visitorService.getOptionalFullVisitorByTelegramId(visitorId);
            String actualQueryStamp = visitor.getScoutQuery().getQueryStamp();

            if (!actualQueryStamp.equals(data[2])) {
                msg.setText(MESSAGE.INVALID.getMsg());
            } else {

                //Visitor visitor = visitorService.getOptionalFullVisitorByTelegramId(visitorId);
                List<String> queryOffers = visitor.getScoutQuery().getQueryOffers();
                String offerToShow = queryOffers.get(page - 1);
                NLAd ad = nlService.getAdById(offerToShow);

                msg.setText(ad.toString());
                msg.setReplyMarkup(InlineKeyboard.getAdsView(page, pagesTotal, actualQueryStamp));

            }

            response.add(msg);

        } else if (option.equals("0")) {
            EditMessageText msg = new EditMessageText();
            msg.setChatId(visitorId);
            msg.setMessageId(messageId);
            msg.setText("Просмотр окончен");
            //msg.setReplyMarkup(InlineKeyboard.getAdsView(1, offerIds.size(), timestamp));

            response.add(msg);
        } //else {
        //  throw new IllegalArgumentException("Wrong data: " + option);
        //}

        if (!response.isEmpty()) {
            return response;
        }

        throw new IllegalArgumentException("Wrong data: " + option);

    }

    @Override
    public AnswerCallbackQuery getAnswerCallbackQuery(String id, String text) {
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(id);
        answer.setText(text);
        return answer;
    }

    private Visitor createVisitor(String visitorId) {
        Visitor newVisitor = new Visitor(visitorId, LocalDateTime.now(), new Subscription(SUBSCRIPTION_TYPE.STANDARD), new ScoutQuery());
        return newVisitor;
    }

}
