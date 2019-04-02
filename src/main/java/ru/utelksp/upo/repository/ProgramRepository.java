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
            " cert.name, cert.dateEnd, pc.name ) " +
            "from Program program " +
            "left join order.employee employee on cert.employee.id = employee.id " +
            "left join cert.computer pc on cert.computer.id = pc.id " +
            "where (:employeeId is null or employee.id = :employeeId) " +
            "and (:certificateId is null or cert.id = :certificateId) " +
            "and (:pcId is null or pc.id = :pcId)")
    List<ProgramReportDto> findWithParam(Long employeeId, Long orderId);
}
