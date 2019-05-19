package ru.utelksp.upo.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import ru.utelksp.upo.UpoApplication;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author Created by ZotovES on 18.05.2019
 */
@ActiveProfiles(profiles = "dev")
@SpringBootTest(classes = UpoApplication.class)
@DisplayName("Тест репозитория сертификатов")
class CertificateRepositoryTest {
    @Autowired
    private CertificateRepository repository;

    @Test
    @DisplayName("Получить все сертификаты")
    void findByAllEagerTest() {
        var certificates = repository.findByAllEager();

        assertFalse(certificates.isEmpty());
    }

    @Test
    @DisplayName("Поиск сертификата по параметрам")
    void findWithParamTest() {
        var certificates = repository.findWithParam(1L, null, 1L);

        assertFalse(certificates.isEmpty());
    }

    @Test
    @DisplayName("Получить сертификаты по идентификатору компьютера")
    void findByComputerIdTest() {
        var certificates = repository.findByComputerId(1L);

        assertFalse(certificates.isEmpty());
    }

    @Test
    @DisplayName("Получить сертификаты по ид пользователя")
    void findByEmployeeIdTest() {
        var certificates = repository.findByEmployeeId(1L);

        assertFalse(certificates.isEmpty());
    }

    @Test
    @DisplayName("Получить сертификаты по части ФИО пользователя")
    void findByLikeEmployeeTest() {
        var page = PageRequest.of(0, 100);
        var certificates = repository.findByLikeEmployee(page, "ива");

        assertFalse(certificates.isEmpty());
    }

    @Test
    @DisplayName("Получить сертификаты по ид программы")
    void findByProgramIdTest() {
        var certificates = repository.findByProgramId(1L);

        assertFalse(certificates.isEmpty());
    }
}