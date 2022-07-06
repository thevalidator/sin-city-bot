/*
 * The Krechet Software
 */
package ru.thekrechetofficial.sincitybot.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.thekrechetofficial.sincitybot.entity.SUBSCRIPTION_TYPE;
import ru.thekrechetofficial.sincitybot.entity.ScoutQuery;
import ru.thekrechetofficial.sincitybot.entity.Subscription;
import ru.thekrechetofficial.sincitybot.entity.Visitor;
import ru.thekrechetofficial.sincitybot.repository.VisitorRepository;
import ru.thekrechetofficial.sincitybot.service.VisitorService;

/**
 * @author theValidator <the.validator@yandex.ru>
 */
@Service
@Transactional
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository repository;

    @Autowired
    public VisitorServiceImpl(VisitorRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveVisitor(Visitor visitor) {
        repository.saveAndFlush(visitor);
    }

    @Override
    public boolean isExistByTelegramId(String id) {
        return repository.existsByTelegramId(id);
    }

    @Override
    public Visitor getVisitorByTelegramId(String id) {
        return repository.findByTelegramId(id).orElseThrow();
    }

    @Override
    public Visitor getFullVisitorByTelegramId(String id) {

        Visitor visitor = repository.findByTelegramId(id).orElseThrow();
        visitor.getScoutQuery().getQueryOffers().size();
        visitor.getSubscription().getRequests();

        return visitor;
    }

//    @Override
//    public String getVisitorsQueryStamp(String id) {
//
//        String queryStamp = repository.findQueryStampByTelegramId(id).orElseThrow();
//
//        return queryStamp;
//    }

    @Override
    public Visitor getOptionalFullVisitorByTelegramId(String id) {

        Visitor visitor;

        Optional<Visitor> optionalVisitor = repository.findByTelegramId(id);
        if (!optionalVisitor.isPresent()) {
            visitor = new Visitor(id, LocalDateTime.now(), new Subscription(SUBSCRIPTION_TYPE.STANDARD), new ScoutQuery());
            visitor.getScoutQuery().setQueryStamp("0");
        } else {
            visitor = optionalVisitor.get();
            visitor.getScoutQuery().getQueryOffers().size();
            visitor.getSubscription().getRequests();
        }
        
        return visitor;

    }
//
//    @Override
//    public Optional<String> getOptionalVisitorsQueryStamp(String id) {
//        return repository.findQueryStampByTelegramId(id);
//    }

}
