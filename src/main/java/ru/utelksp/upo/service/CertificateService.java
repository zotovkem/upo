package ru.utelksp.upo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    Collection<Certificate> findAll();

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

    /**
     * Жадное получение всех сертификатов
     *
     * @return список сертификатов
     */
    Collection<Certificate> findByAllEager();

    /**
     * Поиск сертификатов по ФИО пользователя
     *
     * @param employeeFio фио пользователя
     * @return страница с найдеными сертификатами
     */
    @NonNull
    Page<Certificate> findByLikeEmployee(@NonNull Pageable page, String employeeFio);
}
