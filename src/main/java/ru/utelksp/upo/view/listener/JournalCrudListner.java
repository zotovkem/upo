package ru.utelksp.upo.view.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.vaadin.crudui.crud.CrudListener;
import ru.utelksp.upo.domain.JournalEvent;
import ru.utelksp.upo.service.JournalEventService;

import java.util.Collection;

/**
 * @author Created by ZotovES on 08.04.2019
 * Листнер для формы журнала событий
 */
@Component
@RequiredArgsConstructor
public class JournalCrudListner implements CrudListener<JournalEvent> {
    private final JournalEventService journalEventService;

    /**
     * Получить все записи из Журнала событий
     *
     * @return список событий
     */
    @Override
    public Collection<JournalEvent> findAll() {
        return journalEventService.findAll();
    }

    /**
     * Добавляет событие.
     *
     * @param domainObjectToAdd событие
     * @return сохраненое событие.
     */
    @Override
    public JournalEvent add(JournalEvent domainObjectToAdd) {
        return null;
    }

    /**
     * Редактирует событие
     *
     * @param domainObjectToUpdate событие
     * @return сохраненое событие
     */
    @Override
    public JournalEvent update(JournalEvent domainObjectToUpdate) {
        return null;
    }

    /**
     * Удаляет Событие
     *
     * @param domainObjectToDelete событие
     */
    @Override
    public void delete(JournalEvent domainObjectToDelete) {

    }
}
