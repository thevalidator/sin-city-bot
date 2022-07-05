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
    
    List<NLAd> getNewestByCreatorWithLimit(String creator, int limit);
    
}
