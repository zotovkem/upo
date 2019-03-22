package ru.utelksp.upo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.utelksp.upo.domain.Certificate;

/**
 * @author Created by ZotovES on 22.03.2019
 * Репозитория для работы с Сертификатами
 */
@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
