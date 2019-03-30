package ru.utelksp.upo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.utelksp.upo.domain.Certificate;

import java.util.Collection;

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
}
