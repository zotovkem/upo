package ru.utelksp.upo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.utelksp.upo.domain.Order;

/**
 * @author Created by ZotovES on 22.03.2019
 * Репозиторий для управления Приказами
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
