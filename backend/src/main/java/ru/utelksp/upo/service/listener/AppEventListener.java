package ru.utelksp.upo.service.listener;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.utelksp.upo.domain.JournalEvent;
import ru.utelksp.upo.domain.dictionary.TypeEvent;
import ru.utelksp.upo.domain.event.AgainUserLoginEvent;
import ru.utelksp.upo.domain.event.ErrorAppEvent;
import ru.utelksp.upo.domain.event.LogoutUserEvent;
import ru.utelksp.upo.service.JournalEventService;

import java.time.LocalDate;
import java.time.LocalTime;

import static ru.utelksp.upo.common.enums.TypeEventEnum.*;

/**
 * @author Created by ZotovES on 10.04.2019
 * Листнер событий в приложении
 */
@Component
@AllArgsConstructor
public class AppEventListener {
    private JournalEventService journalEventService;

    /**
     * Ловит событие разлогирования пользователя
     *
     * @param event событие.
     */
    @EventListener
    @Transactional(rollbackFor = Exception.class)
    public void listenLogoutUserEvent(LogoutUserEvent event) {
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
     * Ловит событие попытки входа в систему пользователя
     *
     * @param event событие.
     */
    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void againLoginUserEvent(AgainUserLoginEvent event) {
        var journalEvent = JournalEvent.builder()
                .name("Попытка входа пользователя в приложение")
                .dateEvent(LocalDate.now())
                .timeEvent(LocalTime.now())
                .typeEvent(TypeEvent.builder().id(LOGIN_AGAIN.getTypeEventId()).build())
                .username(event.getUser())
                .description("Не существующий пользователь")
                .build();
        journalEventService.save(journalEvent);
    }

    /**
     * Ловит событие удачного входя в систему пользователя
     *
     * @param event событие.
     */
    @EventListener(AuthenticationSuccessEvent.class)
    @Transactional(rollbackFor = Exception.class)
    public void successLoginUserEvent(AbstractAuthenticationEvent event) {
        var journalEvent = JournalEvent.builder()
                .name("Пользователь удачно авторизовался")
                .dateEvent(LocalDate.now())
                .timeEvent(LocalTime.now())
                .typeEvent(TypeEvent.builder().id(AUTH_USER.getTypeEventId()).build())
                .username(event.getAuthentication().getName())
                .build();
        journalEventService.save(journalEvent);
    }

    /**
     * Ловит ошибки приложения
     *
     * @param event событие.
     */
    @EventListener(ErrorAppEvent.class)
    @Transactional(rollbackFor = Exception.class)
    public void errorEvent(ErrorAppEvent event) {
        var journalEvent = JournalEvent.builder()
                .name("Ошибка приложения")
                .dateEvent(LocalDate.now())
                .timeEvent(LocalTime.now())
                .typeEvent(TypeEvent.builder().id(APP_ERROR.getTypeEventId()).build())
                .username(event.getUser())
                .description(event.getErrorEvent().getThrowable().getLocalizedMessage())
                .build();
        journalEventService.save(journalEvent);
    }


}
