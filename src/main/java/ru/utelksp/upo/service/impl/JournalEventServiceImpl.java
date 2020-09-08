package ru.utelksp.upo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.utelksp.upo.domain.JournalEvent;
import ru.utelksp.upo.repository.JournalEventRepository;
import ru.utelksp.upo.service.JournalEventService;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Created by ZotovES on 07.04.2019
 * Реализация сервиса управления журнала событий
 */
@Service
@RequiredArgsConstructor
public class JournalEventServiceImpl implements JournalEventService {
    private final JournalEventRepository journalEventRepository;

    /**
     * Получить все записи журнала событий
     *
     * @return список событий
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<JournalEvent> findAll() {
        return journalEventRepository.findAll();
    }

    /**
     * Получить событие по идентификатору
     *
     * @param eventId идентификатор события
     * @return событие
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<JournalEvent> findById(@NonNull Long eventId) {
        return journalEventRepository.findById(eventId);
    }

    /**
     * Сохранить событие
     *
     * @param journalEvent событие
     * @return сохраненое событие.
     */
    @Override
    @NonNull
    @Transactional(rollbackFor = Exception.class)
    public JournalEvent save(@NonNull JournalEvent journalEvent) {
        return journalEventRepository.save(journalEvent);
    }
}
