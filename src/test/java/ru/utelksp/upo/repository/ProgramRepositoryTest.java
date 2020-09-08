package ru.utelksp.upo.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.utelksp.upo.UpoApplication;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author Created by ZotovES on 22.05.2019
 */
@ActiveProfiles(profiles = "dev")
@SpringBootTest(classes = UpoApplication.class)
@DisplayName("Тест репозитория программного обеспечения")
class ProgramRepositoryTest {
    @Autowired
    private ProgramRepository repository;

    @Test
    @DisplayName("Получить данные для отчета по набору параметров")
    void findWithParamTest() {
        var programs = repository.findWithParam(1L, 1L, 1L);

        assertFalse(programs.isEmpty());
    }

    @Test
    @DisplayName("Получить список ПО по идентификатору пользователя")
    void findByEmployeeIdTest() {
        var programs = repository.findByEmployeeId(1L);

        assertFalse(programs.isEmpty());
    }

    @Test
    @DisplayName("Получить список ПО по идентификатору вида использования")
    void findByTypeUsingIdTest() {
        var programs = repository.findByTypeUsingId(1L);

        assertFalse(programs.isEmpty());
    }

    @Test
    @DisplayName("Получить список ПО по ид ПК")
    void findByComputerIdTest() {
        var programs = repository.findByComputerId(1L);

        assertFalse(programs.isEmpty());
    }

    @Test
    @DisplayName("Получить список ПО по ид приказа")
    void findByOrderIdTest() {
        var programs = repository.findByOrderId(1L);

        assertFalse(programs.isEmpty());
    }
}