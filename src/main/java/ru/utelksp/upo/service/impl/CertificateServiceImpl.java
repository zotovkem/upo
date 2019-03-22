package ru.utelksp.upo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
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
    public Collection<Certificate> findALL() {
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
    public Certificate save(@NonNull Certificate certificate) {
        return certificateRepository.save(certificate);
    }

    /**
     * Удалить сертификат по идентификатору
     *
     * @param id идентификатор сертификата
     */
    @Override
    public void deleteById(@NonNull Long id) {
        certificateRepository.deleteById(id);
    }
}
