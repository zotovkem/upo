package ru.utelksp.upo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import ru.utelksp.upo.domain.security.User;

import java.util.Optional;

/**
 * @author Created by ZotovES on 31.03.2019
 * Репозиторий управления Пользователями
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Получить пользователя по имени
     *
     * @param name имя пользователя
     * @return пользователь
     */
    Optional<User> findByUsername(@NonNull String name);
}
