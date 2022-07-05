/*
 * The Krechet Software
 */
package ru.thekrechetofficial.sincitybot.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.thekrechetofficial.sincitybot.entity.ad.NLAd;

/**
 *
 * @author theValidator <the.validator@yandex.ru>
 */
@Repository
public interface NLAdRepository extends JpaRepository<NLAd, Long> {
    
    Optional<NLAd> findById(long id);
    
    Optional<NLAd> findByOfferId(long offerId);
    
    @Query(value = "SELECT * "
                    + "FROM public.nlads "
                    + "WHERE creator = ? "
                    + "ORDER BY created_on desc "
                    + "LIMIT ?", nativeQuery = true)
    List<NLAd> findNewestByCreatorWithLimit(String creator, int limit);
    
}
