package ru.utelksp.upo.common;

import com.vaadin.flow.component.notification.Notification;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Created by ZotovES on 26.03.2019
 * Общие утилиты
 */
public class Util {
    public static void showError(String msg) {
        Notification.show(msg);
    }

    /**
     * Собирает из 2 листов мапу
     */
    public static Map<String, String> getCollectMap(String[] arr1, String[] arr2) {
        return IntStream.range(0, arr1.length)
                .boxed()
                .collect(Collectors.toMap(i -> arr1[i], i -> arr2[i]));
    }

    /**
     * Собирает из двух листов мапу по соответвию индексов
     */
    public static Map<String, String> getCollectMap(List<String> list1, List<String> list2) {
        return IntStream.range(0, list1.size())
                .boxed()
                .collect(Collectors.toMap(list1::get, list2::get));
    }
}
