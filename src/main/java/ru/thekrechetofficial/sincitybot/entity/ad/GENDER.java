/*
 * The Krechet Software
 */
package ru.thekrechetofficial.sincitybot.entity.ad;

/**
 *
 * @author theValidator <the.validator@yandex.ru>
 */
public enum Gender {

    MALE("M", "мужчина"),
    FEMALE("F", "женщина"),
    TRANS("T", "транс"),
    COUPLE("C", "пара");

    private final String option;
    private final String textName;

    private Gender(String option, String textName) {
        this.option = option;
        this.textName = textName;
    }

    public String getOption() {
        return option;
    }

    public String getTextName() {
        return textName;
    }

    public static Gender fromString(String text) {
        
        for (Gender g : Gender.values()) {
            if (g.option.equals(text)) {
                return g;
            }
        }
        
        throw new IllegalArgumentException();
    }

}
