/*
 * The Krechet Software
 */
package ru.thekrechetofficial.sincitybot.service.impl;

import com.lowagie.text.Document;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
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
import ru.thekrechetofficial.sincitybot.entity.ad.AbstractAd;
import ru.thekrechetofficial.sincitybot.entity.ad.Gender;
import ru.thekrechetofficial.sincitybot.entity.ad.NLAd;
import ru.thekrechetofficial.sincitybot.service.MessageHandler;
import ru.thekrechetofficial.sincitybot.service.NLAdService;
import ru.thekrechetofficial.sincitybot.service.VisitorService;
import ru.thekrechetofficial.sincitybot.util.pdf.PDFCreator;

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

            } //else if (incomeMsg.equals("/pdf")) {
//                String document = PDFCreator.createAdsPdf(List.of(nlService.getAdById("1")), sheriffId, 0);
//                SendDocument msg = new SendDocument(visitorId, new InputFile(new File(document)));
//                //msg.setChatId(visitorId);
//
//                //response.add(e)
//                //doc.setDocument(document);
//            }

        } else if (incomeMsg.equals(COMMAND.HELP.getCommand())) {
            SendMessage toVisitor = new SendMessage(visitorId, MESSAGE.HELP.getMsg());
            //toVisitor.setReplyMarkup(ReplyKeyboard.getMainKeyboard());                          //TODO: probably not needed
            response.add(toVisitor);
        } else if (incomeMsg.equals(COMMAND.ACCOUNT.getCommand())) {
            Subscription s = visitorService.getVisitorsSubscription(visitorId);
            String message = String.format("КАБИНЕТ\n\n🔹 Тип подписки: %s\n🔹 Осталось запросов: %d\n\n",
                    s.getType().getValue(), s.getRequests());
            SendMessage toVisitor = new SendMessage(visitorId, message);
            //toVisitor.setReplyMarkup(ReplyKeyboard.getMainKeyboard());                          //TODO: probably not needed
            response.add(toVisitor);
        } else if (incomeMsg.equals(COMMAND.SEARCH.getCommand())) {
            SendMessage toVisitor = new SendMessage(visitorId, MESSAGE.CHOOSE_OPTION.getMsg());
            toVisitor.setReplyMarkup(ReplyKeyboard.getSearchKeyboard());
            response.add(toVisitor);
        } else if (incomeMsg.equals(COMMAND.BACK.getCommand())) {
            SendMessage toVisitor = new SendMessage(visitorId, MESSAGE.MAIN_MENU.getMsg());
            toVisitor.setReplyMarkup(ReplyKeyboard.getMainKeyboard());
            response.add(toVisitor);
        } else if (incomeMsg.equals(COMMAND.SCOUT.getCommand())) {
            SendMessage toVisitor = new SendMessage(visitorId, MESSAGE.SEARCH_GENDER.getMsg());
            toVisitor.setReplyMarkup(InlineKeyboard.getGenderOptionForSearch());
            response.add(toVisitor);
        } else if (incomeMsg.equals(COMMAND.TARGET.getCommand())) {
            SendMessage toVisitor = new SendMessage(visitorId, MESSAGE.CONTACT_TARGET.getMsg());
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
    public PartialBotApiMethod<Message> replyMessage(Update update) {

        String visitorId = String.valueOf(update.getMessage().getFrom().getId());
        String queryValue = update.getMessage().getText().trim();
        String query = "%" + queryValue + "%";

        PartialBotApiMethod<Message> response = null;

        Subscription s = visitorService.getVisitorsSubscription(visitorId);
        int requestsLeft = s.getRequests();
        if (requestsLeft > 0 || s.getType().equals(SUBSCRIPTION_TYPE.PREMIUM)) {

            if (queryValue.matches("[a-zA-Z0-9\\.!'@#$%&*()+\\/=?^_`{|}~\\s-]{4,40}")) {

                long totalFound = nlService.getAdsCountByContact(query);

                if (totalFound > 0) {

                    List<NLAd> queryAds = nlService.getAdsForPDFReport(query);

                    //String fileName = PDFCreator.createAdsPdf(queryAds, queryValue, visitorId, totalFound);
                    
                    
                    //
                    ByteArrayOutputStream outputStream = PDFCreator.createAdsPdf(queryAds, queryValue, visitorId, totalFound);
                    ByteArrayInputStream inputstream = new ByteArrayInputStream(outputStream.toByteArray());                    
                    
                    //

                    SendDocument document = new SendDocument(visitorId, new InputFile(inputstream, visitorId + "_" + System.currentTimeMillis() + ".pdf"));
                    document.setReplyMarkup(ReplyKeyboard.getSearchKeyboard());

                    response = document;

                } else {
                    SendMessage msg = new SendMessage(visitorId, MESSAGE.NO_SUCH_CONTACT.getMsg());
                    msg.setReplyMarkup(ReplyKeyboard.getSearchKeyboard());
                    response = msg;
                }
//
//                SendMessage msg = new SendMessage(visitorId, MESSAGE.ADS_FOUND.getMsg() + count);
//                msg.setReplyMarkup(ReplyKeyboard.getSearchKeyboard());
//                response = msg;

                if (s.getType().equals(SUBSCRIPTION_TYPE.STANDARD)) {
                    visitorService.updateRequests(requestsLeft - 1, visitorId);
                }

                //return getSearchResultMsg(DBConnector.getConnection(), update, sb, query);
            } else {

                SendMessage msg = new SendMessage(visitorId, MESSAGE.INVALID_INPUT.getMsg());
                msg.setReplyMarkup(ReplyKeyboard.getSearchKeyboard());
                response = msg;
//            logger.error("[USR] {} [SEARCH] {} (WRONG QUERY)",
//                    update.getMessage().getFrom().getId(), query);
//            return SendMsgBuilder.sendTextMessage(update, "Неверный формат введенных данных. Возможно,"
//                    + " вы ввели слишком короткий текст. Ознакомьтесь с инструкцией по использованию.",
//                    Keyboard.getMainKeyboard());
            }

        } else {
            SendMessage msg = new SendMessage(visitorId, MESSAGE.OUT_OF_REQUESTS.getMsg());
            msg.setReplyMarkup(ReplyKeyboard.getSearchKeyboard());
            response = msg;
        }

        return response;

    }

    @Override
    public List<BotApiMethod> callBackDataMessage(Update update) {

        String option = update.getCallbackQuery().getData();
        String visitorId = String.valueOf(update.getCallbackQuery().getFrom().getId());
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        List<BotApiMethod> response = new ArrayList<>();

        EditMessageText msg = new EditMessageText();
        msg.setChatId(visitorId);
        msg.setMessageId(messageId);

        if (option.matches("^[MFCT]{1}$")) {

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
            visitorService.saveVisitor(visitor);

            NLAd ad = nlService.getAdById(offerIds.get(0));

            msg.setText(ad.toString());
            msg.setReplyMarkup(InlineKeyboard.getAdsView(1, offerIds.size(), timestamp));

            response.add(msg);

        } else if (option.matches("^[0-9]{1,2}-[0-9]{1,2}-[0-9]{1,}$")) {

            String[] data = option.split("-");

            int page = Integer.valueOf(data[0]);
            int pagesTotal = Integer.valueOf(data[1]);

            Visitor visitor = visitorService.getOptionalFullVisitorByTelegramId(visitorId);
            String actualQueryStamp = visitor.getScoutQuery().getQueryStamp();

            if (!actualQueryStamp.equals(data[2])) {
                msg.setText(MESSAGE.INVALID.getMsg());
            } else {

                List<String> queryOffers = visitor.getScoutQuery().getQueryOffers();
                String offerToShow = queryOffers.get(page - 1);
                NLAd ad = nlService.getAdById(offerToShow);

                msg.setText(ad.toString());
                msg.setReplyMarkup(InlineKeyboard.getAdsView(page, pagesTotal, actualQueryStamp));

            }

            response.add(msg);

        } else if (option.equals("0") || response.isEmpty()) {
            msg.setText("Просмотр окончен");
            response.add(msg);
        }

        return response;

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

    //@Override
    public SendDocument generatePDFReport(Update update) {

        String visitorId = String.valueOf(update.getMessage().getFrom().getId());
        String queryValue = update.getMessage().getText().trim();
        String query = "%" + queryValue + "%";

        SendDocument response = null;

//        long totalFound = nlService.getAdsCountByContact(query);
//
//        if (totalFound > 0) {
//
//            List<NLAd> queryAds = nlService.getAdsForPDFReport(query);
//
//            String document = PDFCreator.createAdsPdf(queryAds, queryValue, visitorId, totalFound);
//
//            response = new SendDocument(visitorId, new InputFile(new File(document)));
//            response.setReplyMarkup(ReplyKeyboard.getSearchKeyboard());
//
//        }

        return response;

    }

}
