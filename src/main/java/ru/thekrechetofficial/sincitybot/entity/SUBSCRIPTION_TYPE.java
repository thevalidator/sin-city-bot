/*
 * The Krechet Software
 */
package ru.thekrechetofficial.sincitybot.entity;

/**
 *
 * @author theValidator <the.validator@yandex.ru>
 */
public enum SUBSCRIPTION_TYPE {
    
    PREMIUM("\"Премиум\""),
    STANDARD("\"Стандарт\"");
    
    private final String value;

    private SUBSCRIPTION_TYPE(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
}
