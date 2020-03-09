package ru.utelksp.upo.service.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.utelksp.upo.common.validators.validator.Validator;
import ru.utelksp.upo.common.validators.validator.hints.Create;
import ru.utelksp.upo.common.validators.validator.hints.Update;
import ru.utelksp.upo.domain.security.User;
import ru.utelksp.upo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Created by ZotovES on 18.05.2019
 */
@DisplayName("Тесты сервиса управления учетными записями")
class UserServiceImplTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private Validator validator;
    private UserServiceImpl userService;

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        validator = mock(Validator.class);
        userService = new UserServiceImpl(userRepository, passwordEncoder, validator);
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("Создание пользователя")
    void saveCreateTest() {
        User user = User.builder().build();
        when(userRepository.save(user)).thenReturn(user);
        User savedUser = userService.save(user);

        assertNotNull(savedUser);
        verify(validator).validate(savedUser, Create.class);
        verify(passwordEncoder).encode(any());
        verify(userRepository).save(user);
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("Редактирование пользователя")
    void saveUpdateTest() {
        User user = User.builder().id(1L).build();
        when(userRepository.save(user)).thenReturn(user);
        User savedUser = userService.save(user);

        assertNotNull(savedUser);
        verify(validator).validate(savedUser, Update.class);
        verify(passwordEncoder).encode(any());
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Поиск пользователя по имени")
    void findByUsernameTest() {
        User user = User.builder().id(1L).build();
        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        var savedUser = userService.findByUsername("test");

        assertTrue(savedUser.isPresent());
        verify(userRepository).findByUsername("test");
    }

    @Test
    @DisplayName("Получить всех пользователей")
    void findAllTest() {
        User user = User.builder().id(1L).build();
        when(userRepository.findAll()).thenReturn(List.of(user));
        var savedUser = userService.findAll();

        assertFalse(savedUser.isEmpty());
        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Удалить пользователя")
    void deleteTest() {
        User user = User.builder().id(1L).build();
        userService.delete(user);

        verify(userRepository).deleteById(user.getId());
    }
}