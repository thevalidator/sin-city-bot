/*
 * The Krechet Software
 */
package ru.thekrechetofficial.sincitybot.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
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
    
}
