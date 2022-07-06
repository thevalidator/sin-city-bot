/*
 * The Krechet Software
 */
package ru.thekrechetofficial.sincitybot.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.thekrechetofficial.sincitybot.entity.Visitor;

/**
 *
 * @author theValidator <the.validator@yandex.ru>
 */
@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    Optional<Visitor> findByTelegramId(String id);

    boolean existsByTelegramId(String id);

    @Modifying
    @Query(value = "UPDATE visitor "
                    + "SET requests=? "
                     + "WHERE telegram_id=?", nativeQuery = true)
    public void updateRequests(int requestsNumber, String visitorId);

//    @Query(value = "SELECT v.query_stamp "
//                    + "FROM visitor v "
//                     + "WHERE v.telegram_id = ?", nativeQuery = true)
//    Optional<String> findQueryStampByTelegramId(String id);
}
