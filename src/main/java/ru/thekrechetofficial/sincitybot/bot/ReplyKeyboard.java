/*
 * The Krechet Software
 */

package ru.thekrechetofficial.sincitybot.bot;

import java.util.ArrayList;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

/**
 * @author theValidator <the.validator@yandex.ru>
 */
public class ReplyKeyboard {
    
    public static ReplyKeyboardMarkup getMainKeyboard() {
        
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setSelective(true);
        keyboard.setResizeKeyboard(true);
        //keyboard.setOneTimeKeyboard(true);
        
        ArrayList<KeyboardRow> rows = new ArrayList<>();
        
        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(COMMAND.SEARCH.getCommand());
        firstRow.add(COMMAND.ACCOUNT.getCommand());
        firstRow.add(COMMAND.HELP.getCommand());
        
        rows.add(firstRow);
        
        keyboard.setKeyboard(rows);

        return keyboard;
    }
    
    
    public static ReplyKeyboardMarkup getSearchKeyboard() {
        
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setSelective(true);
        keyboard.setResizeKeyboard(true);
        //keyboard.setOneTimeKeyboard(true);
        
        ArrayList<KeyboardRow> rows = new ArrayList<>();
        
        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(COMMAND.SCOUT.getCommand());
        firstRow.add(COMMAND.TARGET.getCommand());
        firstRow.add(COMMAND.BACK.getCommand());
        
        rows.add(firstRow);
        
        keyboard.setKeyboard(rows);

        return keyboard;
    }

}
