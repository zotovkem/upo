package ru.utelksp.upo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.utelksp.upo.common.dto.CertificateReportDto;
import ru.utelksp.upo.domain.Certificate;

import java.util.Collection;
import java.util.List;

/**
 * @author Created by ZotovES on 22.03.2019
 * Репозитория для работы с Сертификатами
 */
@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    /**
     * Жадное получение всех сертификатов
     *
     * @return список сертификатов
     */
    @Query(value = "" +
            "select cert " +
            "from Certificate cert " +
            "left join fetch cert.employee as employee " +
            "left join fetch cert.computer as pc")
    Collection<Certificate> findByAllEager();

    /**
     * Поиск по параметрам сертификата
     *
     * @param employeeId    идентификатор пользователя
     * @param certificateId идентификатор сертификата
     * @param pcId          иднетификатор компьютера
     * @return список сертификатов
     */
    @Query(value = "" +
            "select new ru.utelksp.upo.common.dto.CertificateReportDto ( " +
            " cert.id, " +
            " concat(employee.lastName,' ' , employee.firstName,' ' , employee.patronymic) , " +
            " cert.name, cert.dateEnd, pc.name ) " +
            "from Certificate cert " +
            "left join cert.employee employee on cert.employee.id = employee.id " +
            "left join cert.computer pc on cert.computer.id = pc.id " +
            "where (:employeeId is null or employee.id = :employeeId) " +
            "and (:certificateId is null or cert.id = :certificateId) " +
            "and (:pcId is null or pc.id = :pcId)")
    List<CertificateReportDto> findWithParam(@Param("employeeId") Long employeeId, @Param("certificateId") Long certificateId, @Param("pcId") Long pcId);

    /**
     * Поиск сертификатов по идентификатору компьютера
     *
     * @param computerId идентификатор компьютера
     * @return список сертификатов
     */
    List<Certificate> findByComputerId(Long computerId);

    /**
     * Поиск сертификатов  по идентификатору пользователя
     *
     * @param employeeId ид пользователя
     * @return страница с найдеными сертификатами
     */
    @Query(value = "" +
            "select distinct cert " +
            "from Certificate cert " +
            "inner join cert.employee employee " +
            "where employee.id = :employeeId ")
    List<Certificate> findByEmployeeId(@Param("employeeId") Long employeeId);

    /**
     * Поиск сертификатов  по ФИО пользователя
     *
     * @param employeeFio фио пользователя
     * @return страница с найдеными сертификатами
     */
    @Query(value = "" +
            "select distinct cert " +
            "from Certificate cert " +
            "left join cert.employee employee " +
            "where :employeeFio is null " +
            "or lower(concat(employee.lastName,' ' , employee.firstName,' ' , employee.patronymic)) like concat('%', lower(:employeeFio), '%') "
    )
    Page<Certificate> findByLikeEmployee(Pageable page, @Param("employeeFio") String employeeFio);
}
