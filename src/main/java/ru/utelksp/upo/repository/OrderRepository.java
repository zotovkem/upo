package ru.utelksp.upo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.utelksp.upo.domain.Order;

import java.util.List;

/**
 * @author Created by ZotovES on 22.03.2019
 * Репозиторий для управления Приказами
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Поиск приказов  по идентификатору пользователя
     *
     * @param employeeId идентификатор пользователя
     * @return список приказов
     */
    @Query(value = "" +
            "select distinct ord " +
            "from Order ord " +
            "left join fetch ord.employees emp " +
            "where emp.id = :employeeId ")
    List<Order> findByComputerId(@Param("employeeId") Long employeeId);

    /**
     * Поиск приказов по идентификатору организации
     *
     * @param organizationId иднетфикатор организации
     * @return список приказов
     */
    List<Order> findByOrganizationId(Long organizationId);

    /**
     * Поиск приказов по ФИО пользователя
     *
     * @param employeeFio фио пользователя
     * @return страница с найдеными ордерами
     */
    @Query(value = "" +
            "select distinct ord " +
            "from Order ord " +
            "left join ord.employees employee " +
            "where :employeeFio is null " +
            "or lower(concat(employee.lastName,' ' , employee.firstName,' ' , employee.patronymic)) like lower(concat('%',:employeeFio,'%') ) ")
    Page<Order> findByLikeEmployee(Pageable page,
                                   @Param("employeeFio") String employeeFio);
}
