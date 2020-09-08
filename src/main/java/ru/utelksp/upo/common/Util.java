package ru.utelksp.upo.common;

import com.vaadin.flow.component.notification.Notification;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Optional.of;

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

    /**
     * Взять первый символ из строки
     *
     * @param source Исходная стсрока
     * @return пустую строку, если исходная - пустая, либо первый символ из исходной строки
     */
    private static String getFirstSymbol(@NonNull String source) {
        return of(source)
                .map(String::trim)
                .filter(n -> !n.isEmpty())
                .map(a -> a.charAt(0))
                .map(c -> Character.toString(c))
                .map(String::toUpperCase)
                .orElse("");
    }

    /**
     * Добавить символ в конец строки
     *
     * @param source Исходная стсрока
     * @param symbol Символ
     * @return пустую строку, если исходная - пустая, либо строку с добавленным в конец символом
     */
    private static String addSymbolToEnd(@NonNull String source, @NonNull String symbol) {
        return of(source)
                .filter(a -> !a.isEmpty())
                .map(a -> a.concat(symbol))
                .orElse("");
    }

    /**
     * Взять первый символ из строки и добавить в конец точку
     *
     * @param source Исходная стсрока
     * @return пустую строку, если исходная - пустая, либо первый символ из исходной строки с точкой на конце
     */
    public static String getFirstSymbolWithDot(@NonNull String source) {
        return of(addSymbolToEnd(getFirstSymbol(source), ".")).orElse("");
    }
}
