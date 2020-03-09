package ru.utelksp.upo.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.utelksp.upo.UpoApplication;
import ru.utelksp.upo.domain.security.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Created by ZotovES on 18.05.2019
 */
@ActiveProfiles(profiles = "dev")
@SpringBootTest(classes = UpoApplication.class)
@DisplayName("Тест репозитория пользователей")
class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Test
    @DisplayName("Поиск по имени пользователя")
    void findByUsername() {
        Optional<User> user = repository.findByUsername("root");

        assertTrue(user.isPresent());
    }
}