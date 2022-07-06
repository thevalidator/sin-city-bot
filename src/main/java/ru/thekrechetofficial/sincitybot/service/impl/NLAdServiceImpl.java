/*
 * The Krechet Software
 */

package ru.thekrechetofficial.sincitybot.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.thekrechetofficial.sincitybot.entity.ad.NLAd;
import ru.thekrechetofficial.sincitybot.repository.NLAdRepository;
import ru.thekrechetofficial.sincitybot.service.NLAdService;

/**
 * @author theValidator <the.validator@yandex.ru>
 */
@Service
@Transactional
public class NLAdServiceImpl implements NLAdService {
    
    private final NLAdRepository repository;

    @Autowired
    public NLAdServiceImpl(NLAdRepository repository) {
        this.repository = repository;        
    }

    @Override
    public List<String> getNewestOfferIdByCreatorWithLimit(String creator, int limit) {
            return repository.findNewestOfferIdByCreatorWithLimit(creator, limit);
    }

    @Override
    public NLAd getAdById(String id) {
        return repository.findById(Long.valueOf(id)).orElseThrow();
    }

    @Override
    public long getAdsCountByContact(String text) {
        
        return repository.countAdsByContactInfo(text);
        
    }
    
    
    

}
