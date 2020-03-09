package ru.utelksp.upo.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.utelksp.upo.common.validators.CertificateValidator;
import ru.utelksp.upo.common.validators.validator.hints.Delete;
import ru.utelksp.upo.common.validators.validator.hints.Update;
import ru.utelksp.upo.domain.Certificate;
import ru.utelksp.upo.repository.CertificateRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;
import static ru.utelksp.upo.utils.ModelSample.*;

/**
 * @author Created by ZotovES on 18.05.2019
 */
@DisplayName("Тестирование сервиса управления сертификатами")
class CertificateServiceImplTest {
    private CertificateServiceImpl certificateService;
    private CertificateRepository certificateRepository;
    private CertificateValidator validator;

    @BeforeEach
    void setUp() {
        certificateRepository = mock(CertificateRepository.class);
        validator = mock(CertificateValidator.class);
        certificateService = new CertificateServiceImpl(certificateRepository, validator);
    }

    @Test
    @DisplayName("Получить все сертификаты")
    void findAllTest() {
        when(certificateRepository.findAll()).thenReturn(List.of(getCertificate(1L)));
        Collection<Certificate> certificates = certificateService.findAll();

        assertFalse(certificates.isEmpty());
        verify(certificateRepository).findAll();
    }

    @Test
    @DisplayName("Получить сертификат по ид")
    void findByIdTest() {
        when(certificateRepository.findById(1L)).thenReturn(Optional.of(getCertificate(1L)));
        var certificates = certificateService.findById(1L);

        assertFalse(certificates.isEmpty());
        verify(certificateRepository).findById(1L);
    }

    @Test
    @DisplayName("Сохранить сертификат")
    void saveUpdateTest() {
        Certificate certificate = getCertificate(1L);
        certificateService.save(certificate);

        verify(certificateRepository).save(certificate);
        verify(validator).validate(certificate, Update.class);
    }

    @Test
    @DisplayName("Удалить сертификат")
    void deleteTest() {
        Certificate certificate = getCertificate(1L);
        certificateService.delete(certificate);

        verify(certificateRepository).delete(certificate);
        verify(validator).validate(certificate, Delete.class);
    }

    @Test
    @DisplayName("Жадное получение всех сертификатов")
    void findByAllEagerTest() {
        when(certificateRepository.findByAllEager()).thenReturn(List.of(getCertificate(1L)));
        Collection<Certificate> certificates = certificateService.findByAllEager();

        assertFalse(certificates.isEmpty());
        verify(certificateRepository).findByAllEager();
    }

    @Test
    @DisplayName("Получить сертификаты по части ФИО пользователя")
    void findByLikeEmployeeTest() {
        when(certificateRepository.findByLikeEmployee(getPagination(), "test")).thenReturn(mapPage(List.of(getCertificate(1L))));
        var certificates = certificateService.findByLikeEmployee(getPagination(), "test");

        assertFalse(certificates.getContent().isEmpty());
        verify(certificateRepository).findByLikeEmployee(getPagination(), "test");
    }

    @Test
    @DisplayName("Получить сертификаты по ид пользователя")
    void findByEmployeeIdTest() {
        when(certificateRepository.findByEmployeeId(1L)).thenReturn(List.of(getCertificate(1L)));
        Collection<Certificate> certificates = certificateService.findByEmployeeId(1L);

        assertFalse(certificates.isEmpty());
        verify(certificateRepository).findByEmployeeId(1L);
    }
}