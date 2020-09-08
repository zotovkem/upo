package ru.utelksp.upo.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.utelksp.upo.UpoApplication;
import ru.utelksp.upo.utils.ModelSample;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author Created by ZotovES on 21.05.2019
 */
@ActiveProfiles(profiles = "dev")
@SpringBootTest(classes = UpoApplication.class)
@DisplayName("Тест репозитория приказов")
class OrderRepositoryTest {
    @Autowired
    private OrderRepository repository;

    @Test
    @DisplayName("Поиск по ид пользователя")
    void findByEmployeeIdTest() {
        var orders = repository.findByEmployeeId(1L);

        assertFalse(orders.isEmpty());
    }

    @Test
    @DisplayName("Поиск по ид организации")
    void findByOrganizationIdTest() {
        var orders = repository.findByOrganizationId(1L);

        assertFalse(orders.isEmpty());
    }

    @Test
    @DisplayName("Поиск по ид сертификата")
    void findByCertificateIdTest() {
        var orders = repository.findByCertificateId(1L);

        assertFalse(orders.isEmpty());
    }

    @Test
    @DisplayName("Поиск по части ФИО пользователя")
    void findByLikeEmployeeTest() {
        var page = ModelSample.getPagination();
        var orders = repository.findByLikeEmployee(page, "ива");

        assertFalse(orders.getContent().isEmpty());

    }
}