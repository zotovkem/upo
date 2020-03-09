package ru.utelksp.upo.domain.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author Created by ZotovES on 08.04.2019
 * Событие удачного входа пользователя
 */
public class SuccessUserLoginEvent extends ApplicationEvent {
    @Getter
    private String user;

    public SuccessUserLoginEvent(Object source, String user) {
        super(source);
        this.user = user;
    }
}
