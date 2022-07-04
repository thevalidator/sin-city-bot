/*
 * The Krechet Software
 */
package ru.thekrechetofficial.sincitybot.bot;

/**
 *
 * @author theValidator <the.validator@yandex.ru>
 */
public enum COMMAND {

    START("/start"),
    SEARCH("Поиск"),
    ACCOUNT("Кабинет"),
    SCOUT("Разведка"),
    TARGET("Розыск"),
    BACK("Назад"),
    HELP("Помощь"); 
    

    private final String command;

    private COMMAND(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
    

}
