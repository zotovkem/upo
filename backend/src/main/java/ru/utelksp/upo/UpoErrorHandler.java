package ru.utelksp.upo;

import com.vaadin.flow.server.ErrorEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import ru.utelksp.upo.domain.event.ErrorAppEvent;
import ru.utelksp.upo.service.SecurityService;

/**
 * @author Created by ZotovES on 10.04.2019
 * Перехватчик ошибок приложения.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UpoErrorHandler implements com.vaadin.flow.server.ErrorHandler {
    private final ApplicationEventPublisher eventPublisher;
    private final SecurityService securityService;

    @Override
    public void error(ErrorEvent errorEvent) {
        eventPublisher.publishEvent(new ErrorAppEvent(this, securityService.currentUser().getUsername(), errorEvent));
        log.error("Error", errorEvent.getThrowable());
    }
}
