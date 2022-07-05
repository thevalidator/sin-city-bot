/*
 * The Krechet Software
 */
package ru.thekrechetofficial.sincitybot.entity.ad;

/**
 *
 * @author theValidator <the.validator@yandex.ru>
 */
public enum GENDER {
    
    MALE("M", "мужчина"),
    FEMALE("F", "женщина"),
    TRANS("T", "транс"),
    COUPLE("C", "пара");
    
    private final String option;
    private final String textName;

    private GENDER(String option, String textName) {
        this.option = option;
        this.textName = textName;
    }

    public String getOption() {
        return option;
    }
    
    public String getTextName() {
        return textName;
    }
    
    
}
