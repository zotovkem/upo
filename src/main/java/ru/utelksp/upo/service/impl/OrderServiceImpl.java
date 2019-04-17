package ru.utelksp.upo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.utelksp.upo.common.validators.OrderValidator;
import ru.utelksp.upo.common.validators.validator.hints.Delete;
import ru.utelksp.upo.domain.Order;
import ru.utelksp.upo.repository.OrderRepository;
import ru.utelksp.upo.service.OrderService;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Created by ZotovES on 22.03.2019
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderValidator validator;

    /**
     * Получить все приказы
     *
     * @return список приказов
     */
    @NonNull
    @Override
    @Transactional(readOnly = true)
    public Collection<Order> findAll() {
        return orderRepository.findAll();
    }

    /**
     * Получить приказ по идентификатору
     *
     * @param id идентификатор приказа
     * @return приказ
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findById(@NonNull Long id) {
        return orderRepository.findById(id);
    }

    /**
     * Сохранить приказ
     *
     * @param order приказ
     * @return сохраненый приказ
     */
    @NonNull
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order save(@NonNull Order order) {
        return orderRepository.save(order);
    }

    /**
     * Удалить приказ
     *
     * @param order приказа
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(@NonNull Order order) {
        validator.validate(order, Delete.class);
        orderRepository.deleteById(order.getId());
    }

    /**
     * Поиск приказов по ФИО пользователя
     *
     * @param employeeFio фио пользователя
     * @return страница с найдеными ордерами
     */
    @Override
    public Page<Order> findByLikeEmployee(Pageable page, String employeeFio) {
        return orderRepository.findByLikeEmployee(page, employeeFio);
    }
}
