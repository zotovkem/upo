package ru.utelksp.upo.domain.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author Created by ZotovES on 08.04.2019
 * Событие разлогирования пользователя
 */
public class LogoutUserEvent extends ApplicationEvent {
    @Getter
    private String user;

    public LogoutUserEvent(Object source, String user) {
        super(source);
        this.user = user;
    }
}
