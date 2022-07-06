/*
 * The Krechet Software
 */
package ru.thekrechetofficial.sincitybot.service;

import java.util.Optional;
import ru.thekrechetofficial.sincitybot.entity.Visitor;

/**
 *
 * @author theValidator <the.validator@yandex.ru>
 */
public interface VisitorService {
    
    void saveVisitor(Visitor visitor);
    
    boolean isExistByTelegramId(String id);
    
    Visitor getVisitorByTelegramId(String id);
    
    Visitor getFullVisitorByTelegramId(String id);
    
//    String getVisitorsQueryStamp(String id);
    
    Visitor getOptionalFullVisitorByTelegramId(String id);
    
//    Optional<String> getOptionalVisitorsQueryStamp(String id);
    
}
