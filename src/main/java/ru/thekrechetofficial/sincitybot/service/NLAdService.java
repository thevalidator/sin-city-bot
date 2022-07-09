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

    NLAd getAdById(String id);

    long getAdsCountByContact(String text);
    
    List<NLAd> getAdsForPDFReport(String contact);
    
}
