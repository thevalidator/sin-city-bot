/*
 * The Krechet Software
 */
package ru.thekrechetofficial.sincitybot.bot;

import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

/**
 * @author theValidator <the.validator@yandex.ru>
 */
public class InlineKeyboard {

    public static InlineKeyboardMarkup getMainKeyboard() {

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        InlineKeyboardButton searchButton = new InlineKeyboardButton();
        searchButton.setText("Поиск");
        searchButton.setCallbackData("SRCH");
        
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(searchButton);
        
        
        InlineKeyboardButton helpButton = new InlineKeyboardButton();
        helpButton.setText("Помощь");
        helpButton.setCallbackData("HLP");
        
        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        thirdRow.add(helpButton);
        
        
        
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(firstRow);
        rows.add(thirdRow);
        
        keyboard.setKeyboard(rows);
        
        return keyboard;
    }

}
