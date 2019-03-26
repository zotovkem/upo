package ru.utelksp.upo.common;

import com.vaadin.flow.component.notification.Notification;

/**
 * @author Created by ZotovES on 26.03.2019
 * Общие утилиты
 */
public class Util {
    public void showError(String msg) {
        Notification.show(msg);
    }

    public void showSaveNotification(String msg) {
        Notification.show(msg);
    }
}
