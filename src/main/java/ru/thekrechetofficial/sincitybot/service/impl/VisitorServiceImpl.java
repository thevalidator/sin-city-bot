/*
 * The Krechet Software
 */

package ru.thekrechetofficial.sincitybot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    
    
    
    

}
