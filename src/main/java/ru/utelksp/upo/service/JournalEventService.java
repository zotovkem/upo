package ru.utelksp.upo.service;

import org.springframework.lang.NonNull;
import ru.utelksp.upo.domain.JournalEvent;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Created by ZotovES on 07.04.2019
 * Сервис для журнала событий
 */
public interface JournalEventService {
    /**
     * Получить все записи журнала событий
     *
     * @return список событий
     */
    Collection<JournalEvent> findAll();

    /**
     * Получить событие по идентификатору
     *
     * @param eventId идентификатор события
     * @return событие
     */
    Optional<JournalEvent> findById(@NonNull Long eventId);

    /**
     * Сохранить событие
     *
     * @param journalEvent событие
     * @return сохраненое событие.
     */
    @NonNull
    JournalEvent save(@NonNull JournalEvent journalEvent);
}
