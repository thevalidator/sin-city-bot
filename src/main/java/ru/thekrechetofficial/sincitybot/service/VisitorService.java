/*
 * The Krechet Software
 */
package ru.thekrechetofficial.sincitybot.service;

import ru.thekrechetofficial.sincitybot.entity.Visitor;

/**
 *
 * @author theValidator <the.validator@yandex.ru>
 */
public interface VisitorService {
    
    void saveVisitor(Visitor visitor);
    
    Visitor getVisitorByTelegramId(String id);
    
    Visitor getFullVisitorByTelegramId(String id);
    
    boolean isExistByTelegramId(String id);
    
}
