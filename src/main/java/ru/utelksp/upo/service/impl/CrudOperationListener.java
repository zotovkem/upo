package ru.utelksp.upo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import ru.utelksp.upo.common.enums.TypeEventEnum;
import ru.utelksp.upo.domain.JournalEvent;
import ru.utelksp.upo.domain.dictionary.TypeEvent;
import ru.utelksp.upo.domain.event.LogoutUserEvent;
import ru.utelksp.upo.service.JournalEventService;
import ru.utelksp.upo.service.SecurityService;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import static ru.utelksp.upo.common.enums.TypeEventEnum.*;

/**
 * @author Created by ZotovES on 07.04.2019
 * Листнер слушает crud события энтити манеджера и записывает в аудит жкрнал.
 */
@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class CrudOperationListener {
    @Autowired
    private SecurityService securityService;
    @Autowired
    private JournalEventService journalEventService;

    /**
     * Ловит событие разлогирования пользователя
     *
     * @param event событие.
     */
    @EventListener(value = LogoutUserEvent.class)
    @Transactional(rollbackFor = Exception.class)
    public void listenLogoutUserEvent(LogoutUserEvent event) {
        init();
        var journalEvent = JournalEvent.builder()
                .name("Выход пользователя из приложения")
                .dateEvent(LocalDate.now())
                .timeEvent(LocalTime.now())
                .typeEvent(TypeEvent.builder().id(LOGOUT_USER.getTypeEventId()).build())
                .username(event.getUser())
                .build();
        journalEventService.save(journalEvent);
    }

    /**
     * Ловит событие создания сущности
     *
     * @param entity сущность
     */
    @PostPersist
    @Transactional(rollbackFor = Exception.class)
    public void postPersist(Object entity) {
        var event = getJournalEvent(entity, ADD_ENTITY);
        journalEventService.save(event);
    }

    /**
     * Ловит событие сохранения сущности
     *
     * @param o сущность
     */
    @PostRemove
    @Transactional(rollbackFor = Exception.class)
    public void postRemove(Object o) {
        var event = getJournalEvent(o, UPDATE_ENTITY);
        journalEventService.save(event);

    }

    /**
     * Ловит событие редактирования сущности
     *
     * @param o сущность
     */
    @PostUpdate
    @Transactional(rollbackFor = Exception.class)
    public void postUpdate(Object o) {
        var event = getJournalEvent(o, REMOVE_ENTITY);
        journalEventService.save(event);
    }

    /**
     * Собирает событие для записи в журнал
     *
     * @param o сущность
     */
    private JournalEvent getJournalEvent(Object o, TypeEventEnum typeEvent) {
        init();
        return JournalEvent.builder()
                .name("Операции с информационными материалами")
                .dateEvent(LocalDate.now())
                .timeEvent(LocalTime.now())
                .typeEvent(TypeEvent.builder().id(typeEvent.getTypeEventId()).build())
                .username(securityService.currentUsername().orElse(""))
                .description(truncateString(o.toString(), 3500))
                .build();
    }

    /**
     * Обрезает строку если она больше
     *
     * @param obj   строка
     * @param count колво символов
     * @return итоговая строка
     */
    private String truncateString(String obj, int count) {
        if (obj.length() <= count) {
            return obj;
        } else {
            return obj.substring(0, 3500);
        }
    }

    /**
     * Инежектит зависимости сервисов
     */
    private void init() {
        if (Objects.isNull(securityService) || Objects.isNull(journalEventService)) {
            AutowireHelper.autowire(this);
        }
    }

}
