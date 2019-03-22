package ru.utelksp.upo.service;

import org.springframework.lang.NonNull;
import ru.utelksp.upo.domain.Certificate;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Created by ZotovES on 22.03.2019
 * Сервис для управления Сертификатами
 */
public interface CertificateService {
    /**
     * Возвращает список всех сертификатов
     *
     * @return список сертификатов
     */
    Collection<Certificate> findALL();

    /**
     * Получить сертификат по идентификатору
     *
     * @param id идентификатор сертификата
     * @return сертификат
     */
    Optional<Certificate> findById(@NonNull Long id);

    /**
     * Сохранить сертификат
     *
     * @param certificate сертификат
     * @return сохраненый сертификат
     */
    @NonNull
    Certificate save(@NonNull Certificate certificate);

    /**
     * Удалить сертификат по идентификатору
     *
     * @param id идентификатор сертификата
     */
    void deleteById(@NonNull Long id);
}
