package ru.utelksp.upo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.utelksp.upo.domain.Certificate;
import ru.utelksp.upo.repository.CertificateRepository;
import ru.utelksp.upo.service.CertificateService;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Created by ZotovES on 22.03.2019
 * Реализация сервиса управления сертификатами
 */
@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;

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
     * @param id идентификатор сертификата
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(@NonNull Long id) {
        certificateRepository.deleteById(id);
    }

    @Override
    public Collection<Certificate> findByAllEager() {
        return certificateRepository.findByAllEager();
    }
}
