/*
 * The Krechet Software
 */
package ru.thekrechetofficial.sincitybot.service;

import ru.thekrechetofficial.sincitybot.entity.Subscription;
import ru.thekrechetofficial.sincitybot.entity.Visitor;

/**
 *
 * @author theValidator <the.validator@yandex.ru>
 */
public interface VisitorService {

    void updateRequests(int requestsNumber, String visitorId);
    
    void addRequests(int requestsNumber, String visitorId);
    
    void saveVisitor(Visitor visitor);

    boolean isExistByTelegramId(String id);

    Visitor getVisitorByTelegramId(String id);

    Visitor getFullVisitorByTelegramId(String id);

    Visitor getOptionalFullVisitorByTelegramId(String id);

    Subscription getVisitorsSubscription(String visitorId);
    
}
