package ru.utelksp.upo.view.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.data.spring.OffsetBasedPageRequest;
import ru.utelksp.upo.domain.Order;
import ru.utelksp.upo.service.OrderService;

import java.util.Collection;

/**
 * @author Created by ZotovES on 31.03.2019
 * Листнер управления Приказами
 */
@Component
@RequiredArgsConstructor
public class OrderCrudListener implements CrudListener<Order> {
    private final OrderService orderService;

    @Override
    public Collection<Order> findAll() {
        return orderService.findAll();
    }

    @Override
    public Order add(Order domainObjectToAdd) {
        return orderService.save(domainObjectToAdd);
    }

    @Override
    public Order update(Order domainObjectToUpdate) {
        return orderService.save(domainObjectToUpdate);
    }

    @Override
    public void delete(Order domainObjectToDelete) {
        orderService.delete(domainObjectToDelete);
    }

    public Collection<Order> findByLikeEmployee(String fio, int limit, int offset) {
        Pageable pageable = new OffsetBasedPageRequest(offset, limit);
        return orderService.findByLikeEmployee(pageable, fio.isEmpty() ? null : fio).getContent();
    }

    public int countByLikeEmployee(String fio, int limit, int offset) {
        return (int) orderService.findByLikeEmployee(PageRequest.of(offset, limit), fio.isEmpty() ? null : fio).getTotalElements();
    }

}
