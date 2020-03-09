package ru.utelksp.upo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import ru.utelksp.upo.domain.Order;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Created by ZotovES on 22.03.2019
 * Сервис управления Приказами
 */
public interface OrderService {
    /**
     * Получить все приказы
     * @return список приказов
     */
    @NonNull
    Collection<Order> findAll();

    /**
     * Получить приказ по идентификатору
     * @param id идентификатор приказа
     * @return приказ
     */
    Optional<Order> findById(@NonNull Long id);

    /**
     * Сохранить приказ
     * @param order приказ
     * @return сохраненый приказ
     */
    @NonNull
    Order save(@NonNull Order order);

    /**
     * Удалить приказ
     * @param order приказа
     */
    void delete(@NonNull Order order);

    /**
     * Поиск приказов по ФИО пользователя
     *
     * @param employeeFio фио пользователя
     * @return страница с найдеными ордерами
     */
    Page<Order> findByLikeEmployee(Pageable page, String employeeFio);
}
