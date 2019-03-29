package ru.utelksp.upo.common;

import com.vaadin.flow.component.notification.Notification;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public static Map<String, String> getCollectMap(String[] arr1, String[] arr2) {
        return IntStream.range(0, arr1.length)
                .boxed()
                .collect(Collectors.toMap(i -> arr1[i], i -> arr2[i]));
    }
}
