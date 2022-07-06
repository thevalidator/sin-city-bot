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

//    @Query(value = "SELECT foo.offerid "
//            + "FROM (SELECT DISTINCT offerid, created_on "
//                + "FROM nlads n "
//                + "WHERE creator = ? "
//                + "ORDER BY created_on desc "
//                + "LIMIT ?"
//            + ") AS foo", nativeQuery = true)
    @Query(value = "SELECT n.id "
                    + "FROM nlads n "
                        + "WHERE creator = ? "
                            + "ORDER BY created_on desc "
                              + "LIMIT ?", nativeQuery = true)
    List<String> findNewestOfferIdByCreatorWithLimit(String creator, int limit);

}
