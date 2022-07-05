/*
 * The Krechet Software
 */

package ru.thekrechetofficial.sincitybot.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    public List<NLAd> getNewestByCreatorWithLimit(String creator, int limit) {
            return repository.findNewestByCreatorWithLimit(creator, limit);
    }
    

}
