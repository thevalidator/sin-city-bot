/*
 * The Krechet Software
 */
package ru.thekrechetofficial.sincitybot.service;

import java.util.List;
import ru.thekrechetofficial.sincitybot.entity.ad.NLAd;

/**
 *
 * @author theValidator <the.validator@yandex.ru>
 */
public interface NLAdService {
    
    List<String> getNewestOfferIdByCreatorWithLimit(String creator, int limit);

    public NLAd getAdByOfferId(String get);
    
}
