package ru.utelksp.upo.service;

import org.springframework.lang.NonNull;
import ru.utelksp.upo.domain.security.User;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Created by ZotovES on 31.03.2019
 * Сервис управления Пользователями
 */

public interface UserService {

    /**
     * Сохраняет пользователя
     *
     * @param user пользователь
     * @return сохраненый пользователь
     */
    User save(User user);

    /**
     * Ищет пользователя по имени
     *
     * @param username имя пользоваиеля
     * @return пользователь
     */
    Optional<User> findByUsername(@NonNull String username);

    /**
     * Получить всех пользователей
     *
     * @return список пользователей
     */
    Collection<User> findAll();

    /**
     * Удаляет пользователя по идентификатору
     *
     * @param user пользователь
     */
    void delete(@NonNull User user);
}
