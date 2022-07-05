/*
 * The Krechet Software
 */
package ru.thekrechetofficial.sincitybot.bot;

import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.thekrechetofficial.sincitybot.entity.ad.GENDER;

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
    
    
    
    public static InlineKeyboardMarkup getGenderOptionForSearch() {
        
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        List<InlineKeyboardButton> secondRow = new ArrayList<>();

        InlineKeyboardButton btnM = new InlineKeyboardButton();
        btnM.setText("мужчин");
        btnM.setCallbackData(GENDER.MALE.getOption());
        InlineKeyboardButton btnF = new InlineKeyboardButton();
        btnF.setText("женщин");
        btnF.setCallbackData(GENDER.FEMALE.getOption());
        
       
        InlineKeyboardButton btnC = new InlineKeyboardButton();
        btnC.setText("пары");
        btnC.setCallbackData(GENDER.COUPLE.getOption());
        InlineKeyboardButton btnT = new InlineKeyboardButton();
        btnT.setText("трансов");
        btnT.setCallbackData(GENDER.TRANS.getOption());

        firstRow.add(btnM);
        firstRow.add(btnF);
        secondRow.add(btnC);
        secondRow.add(btnT);
        rows.add(firstRow);
        rows.add(secondRow);
        keyboard.setKeyboard(rows);

        return keyboard;
        
    }
    
    public static InlineKeyboardMarkup getNumOptionForSearch(String option) {
        
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        
        String btnOneText = "1";
        String btnTwoText = "5";
        String btnThreeText = "10";
        String btnFourText = "15";

        InlineKeyboardButton btnOne = new InlineKeyboardButton();
        btnOne.setText(btnOneText);
        btnOne.setCallbackData(option + " " + btnOneText);
        InlineKeyboardButton btnTwo = new InlineKeyboardButton();
        btnTwo.setText(btnTwoText);
        btnTwo.setCallbackData(option + " " + btnTwoText);
        InlineKeyboardButton btnThree = new InlineKeyboardButton();
        btnThree.setText(btnThreeText);
        btnThree.setCallbackData(option + " " + btnThreeText);
        InlineKeyboardButton btnFour = new InlineKeyboardButton();
        btnFour.setText(btnFourText);
        btnFour.setCallbackData(option + " " + btnFourText);

        firstRow.add(btnOne);
        firstRow.add(btnTwo);
        firstRow.add(btnThree);
        firstRow.add(btnFour);
        
        rows.add(firstRow);
        keyboard.setKeyboard(rows);

        return keyboard;
        
    }

}
