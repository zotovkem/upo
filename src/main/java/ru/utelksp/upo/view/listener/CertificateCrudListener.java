package ru.utelksp.upo.view.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.vaadin.crudui.crud.CrudListener;
import ru.utelksp.upo.domain.Certificate;
import ru.utelksp.upo.service.CertificateService;

import java.util.Collection;

/**
 * @author Created by ZotovES on 30.03.2019
 * Листнер для формы Сертификатов
 */
@Component
@RequiredArgsConstructor
public class CertificateCrudListener implements CrudListener<Certificate> {
    private final CertificateService certificateService;

    /**
     * Получить все сертификаты
     *
     * @return список сертификатов
     */
    @Override
    public Collection<Certificate> findAll() {
        return certificateService.findByAllEager();
    }

    /**
     * Добавить сертификат
     *
     * @param certificate сертификат
     * @return сохраненый сертификат
     */
    @Override
    public Certificate add(Certificate certificate) {
        return certificateService.save(certificate);
    }

    /**
     * Обновить сертификат
     *
     * @param certificate сертификат
     * @return сохраненый сертификат
     */
    @Override
    public Certificate update(Certificate certificate) {
        return certificateService.save(certificate);
    }

    /**
     * Удалить сертификат
     * @param certificate сертификат
     */
    @Override
    public void delete(Certificate certificate) {
        certificateService.deleteById(certificate.getId());
    }

    /**
     * Поиск сертификата по части фио пользователя
     *
     * @param fio    фио пользователя
     * @param limit  кол-во записей
     * @param offset с какой записи отображать
     * @return список сертификатов
     */
    public Collection<Certificate> findByLikeEmployee(String fio, int limit, int offset) {
        return certificateService.findByLikeEmployee(PageRequest.of(offset, limit), fio.isEmpty() ? null : fio).getContent();
    }

    /**
     * Получить кол-во найденых сертификатов по части фио пользователя
     *
     * @param fio    фио пользователя
     * @param limit  кол-во записей
     * @param offset с какой записи отображать
     * @return кол-во сертификатов
     */
    public int countByLikeEmployee(String fio, int limit, int offset) {
        return (int) certificateService.findByLikeEmployee(PageRequest.of(offset, limit), fio.isEmpty() ? null : fio).getTotalElements();
    }
}
