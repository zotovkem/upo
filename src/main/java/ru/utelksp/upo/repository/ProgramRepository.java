package ru.utelksp.upo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
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
            "and (:orderId is null or ord.id = :orderId) " +
            "and (:typeUsingId is null or program.typeUsing.id = :typeUsingId)")
    List<ProgramReportDto> findWithParam(@Param("employeeId") Long employeeId, @Param("orderId") Long orderId, @Param("typeUsingId") Long typeUsingId);

    /**
     * Полуяить список ПО по идентификатору пользователя
     *
     * @return список программ
     */
    @Query(value = "" +
            "select distinct program " +
            "from Certificate cert " +
            "inner join cert.programs program " +
            "inner join cert.employee emp " +
            "where emp.id = :employeeId")
    List<Program> findByEmployeeId(@NonNull @Param("employeeId") Long employeeId);

    /**
     * Поиск по виду использования ПО
     *
     * @param typeUsingId идентификатор вида использования
     * @return список программ.
     */
    List<Program> findByTypeUsingId(Long typeUsingId);

    /**
     * Поиск программы по идентификатору компьютера
     *
     * @param computerId идентификатор компьютера
     * @return список программ
     */
    @Query(value = "" +
            "select distinct program " +
            "from Program program " +
            "left join fetch program.computers comp " +
            "where comp.id = :computerId ")
    List<Program> findByComputerId(@Param("computerId") Long computerId);

    /**
     * Поиск программы по идентификатору приказа
     *
     * @param orderId идентификатор приказа
     * @return список программ
     */
    @Query(value = "" +
            "select distinct program " +
            "from Program program " +
            "left join fetch program.orders ord " +
            "where ord.id = :orderId ")
    List<Program> findByOrderId(@Param("orderId") Long orderId);
}
