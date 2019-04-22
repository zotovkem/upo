package ru.utelksp.upo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.utelksp.upo.common.validators.CertificateValidator;
import ru.utelksp.upo.common.validators.validator.hints.Delete;
import ru.utelksp.upo.domain.Certificate;
import ru.utelksp.upo.repository.CertificateRepository;
import ru.utelksp.upo.service.CertificateService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 22.03.2019
 * Реализация сервиса управления сертификатами
 */
@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final CertificateValidator validator;

    /**
     * Возвращает список всех сертификатов
     *
     * @return список сертификатов
     */
    @NonNull
    @Override
    @Transactional(readOnly = true)
    public Collection<Certificate> findAll() {
        return certificateRepository.findAll();
    }

    /**
     * Получить сертификат по идентификатору
     *
     * @param id идентификатор сертификата
     * @return сертификат
     */
    @NonNull
    @Override
    @Transactional(readOnly = true)
    public Optional<Certificate> findById(@NonNull Long id) {
        return certificateRepository.findById(id);
    }

    /**
     * Сохранить сертификат
     *
     * @param certificate сертификат
     * @return сохраненый сертификат
     */
    @NonNull
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Certificate save(@NonNull Certificate certificate) {
        return certificateRepository.save(certificate);
    }

    /**
     * Удалить сертификат по идентификатору
     *
     * @param certificate сертификата
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(@NonNull Certificate certificate) {
        validator.validate(certificate, Delete.class);
        certificateRepository.delete(certificate);
    }

    /**
     * Жадное получение списка всех сертификатов
     *
     * @return список сертификатов
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<Certificate> findByAllEager() {
        return certificateRepository.findByAllEager();
    }

    /**
     * Поиск сертификатов по ФИО пользователя
     *
     * @param page        страница со списком сертификатов
     * @param employeeFio фио пользователя
     * @return страница с найдеными сертификатами
     */
    @Override
    @NonNull
    public Page<Certificate> findByLikeEmployee(@NonNull Pageable page, String employeeFio) {
        return certificateRepository.findByLikeEmployee(page, employeeFio);
    }

    /**
     * Получить список сертификатоф по идентификатору пользователя
     *
     * @param employeeId ид пользователя
     * @return список программ
     */
    @Override
    public List<Certificate> findByEmployeeId(@NonNull Long employeeId) {
        return certificateRepository.findByEmployeeId(employeeId);
    }
}
