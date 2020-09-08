package ru.utelksp.upo.utils;


import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.utelksp.upo.domain.Certificate;

import java.util.List;

/**
 * @author Created by ZotovES on 19.05.2019
 * Фабрики для генерации объектов для тестов
 */
public class ModelSample {

    /**
     * Получить объект пагинации с идентификатором
     *
     * @param id идентификатор
     * @return сертификат
     */
    public static Certificate getCertificate(Long id) {
        return Certificate.builder()
                .id(id)
                .build();
    }

    /**
     * Получить страницу пагинации
     *
     * @param page номер страницы
     * @param size размер страницы
     * @return страница
     */
    @SuppressWarnings("WeakerAccess")
    public static PageRequest getPagination(int page, int size) {
        return PageRequest.of(page, size);
    }

    /**
     * Получить страницу пагинации
     *
     * @return страница
     */
    public static PageRequest getPagination() {
        return getPagination(0, 100);
    }

    /**
     * Получить страницу из коллекции
     *
     * @param list коллекция
     * @param <T>  тип
     * @return страница
     */
    public static <T> PageImpl<T> mapPage(List<T> list) {
        return new PageImpl<>(list);
    }

}
