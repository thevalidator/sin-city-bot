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

    Optional<NLAd> findByOfferId(String offerId);
    
    //SELECT * FROM nlads where contact ilike '%kAlinka%' order by created_on desc limit 10;
    
    @Query(value = "SELECT n.* "
                    + "FROM nlads n "
                        + "WHERE n.contact ilike ? "
                            + "ORDER BY n.created_on desc "
                              + "LIMIT ?", nativeQuery = true)
    List<NLAd> findByContactLikeWithLimit(String contact, int limit);
    
    @Query(value = "SELECT n.id "
                    + "FROM nlads n "
                        + "WHERE n.creator = ? "
                            + "ORDER BY n.created_on desc "
                              + "LIMIT ?", nativeQuery = true)
    List<String> findNewestOfferIdByCreatorWithLimit(String creator, int limit);
    
    @Query(value = "SELECT COUNT(n.id) "
                    + "FROM nlads n "
                        + "WHERE contact "
                          + "ILIKE ?", nativeQuery = true)
    public long countAdsByContactInfo(String text);

}
