/*
 * The Krechet Software
 */
package ru.thekrechetofficial.sincitybot.entity;

/**
 *
 * @author theValidator <the.validator@yandex.ru>
 */
public enum GENDER {
    
    MALE("M"),
    FEMALE("F"),
    COUPLE("C"),
    TRANS("T");
    
    String option;

    private GENDER(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }
    
    
}
