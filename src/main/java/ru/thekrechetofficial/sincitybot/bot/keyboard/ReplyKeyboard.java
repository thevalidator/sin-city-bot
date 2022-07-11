/*
 * The Krechet Software
 */

package ru.thekrechetofficial.sincitybot.bot.keyboard;

import java.util.ArrayList;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.thekrechetofficial.sincitybot.bot.COMMAND;

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
        firstRow.add(COMMAND.SCOUT.getCommand());
        firstRow.add(COMMAND.TARGET.getCommand());
        
        KeyboardRow secondRow = new KeyboardRow();
        secondRow.add(COMMAND.ACCOUNT.getCommand());
        secondRow.add(COMMAND.HELP.getCommand());
        
        rows.add(firstRow);
        rows.add(secondRow);
        
        keyboard.setKeyboard(rows);

        return keyboard;
    }
    
    
//    public static ReplyKeyboardMarkup getSearchKeyboard() {
//        
//        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
//        keyboard.setSelective(true);
//        keyboard.setResizeKeyboard(true);
//        //keyboard.setOneTimeKeyboard(true);
//        
//        ArrayList<KeyboardRow> rows = new ArrayList<>();
//        
//        KeyboardRow firstRow = new KeyboardRow();
//        firstRow.add(COMMAND.SCOUT.getCommand());
//        firstRow.add(COMMAND.TARGET.getCommand());
//        firstRow.add(COMMAND.BACK.getCommand());
//        
//        rows.add(firstRow);
//        
//        keyboard.setKeyboard(rows);
//
//        return keyboard;
//    }
    
    public static ForceReplyKeyboard getReplyOnRequestKeyboard() {
        ForceReplyKeyboard keyboard = new ForceReplyKeyboard();
        keyboard.setForceReply(true);
        keyboard.setSelective(false);

        return keyboard;
    }

}
