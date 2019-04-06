package ru.utelksp.upo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.utelksp.upo.common.dto.ProgramReportDto;
import ru.utelksp.upo.domain.Program;

import java.util.List;

/**
 * @author Created by ZotovES on 23.03.2019
 * Репозиторй для управления программами
 */
@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    /**
     * Поиск программы по параметрам
     *
     * @param employeeId идентификатор пользователя
     * @param orderId    идентификатор приказа
     * @return список приказов
     */
    @Query(value = "" +
            "select new ru.utelksp.upo.common.dto.ProgramReportDto ( " +
            " program.id, " +
            " program.name, " +
            "concat(emp.lastName,' ' , emp.firstName,' ' , emp.patronymic) , " +
            "ord.orderDate, " +
            "ord.orderNumber ) " +
            "from Program program " +
            "left join program.orders ord " +
            "left join ord.employees emp " +
            "where (:employeeId is null or emp.id = :employeeId) " +
            "and (:orderId is null or ord.id = :orderId)")
    List<ProgramReportDto> findWithParam(Long employeeId, Long orderId);
}
