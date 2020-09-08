package ru.utelksp.upo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.utelksp.upo.domain.JournalEvent;

/**
 * @author Created by ZotovES on 07.04.2019
 * Репозиторий для журнала событий в приложении
 */
@Repository
public interface JournalEventRepository extends JpaRepository<JournalEvent, Long> {
}
