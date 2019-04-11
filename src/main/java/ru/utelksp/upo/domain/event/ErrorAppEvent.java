package ru.utelksp.upo.domain.event;

import com.vaadin.flow.server.ErrorEvent;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author Created by ZotovES on 08.04.2019
 * Событие ошибки в приложении
 */
public class ErrorAppEvent extends ApplicationEvent {
    @Getter
    private String user;
    @Getter
    private ErrorEvent errorEvent;

    public ErrorAppEvent(Object source, String user, ErrorEvent errorEvent) {
        super(source);
        this.user = user;
        this.errorEvent = errorEvent;
    }
}
