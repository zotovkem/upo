package ru.utelksp.upo.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.utelksp.upo.common.exception.ValidationException;
import ru.utelksp.upo.common.validators.validator.Validator;
import ru.utelksp.upo.common.validators.validator.hints.Create;
import ru.utelksp.upo.common.validators.validator.hints.Delete;
import ru.utelksp.upo.common.validators.validator.hints.Update;
import ru.utelksp.upo.domain.security.User;
import ru.utelksp.upo.repository.UserRepository;
import ru.utelksp.upo.service.UserService;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Created by ZotovES on 18.03.2019
 * Реализация сервиса управления пользователями
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Validator<User> validator;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User save(User user) throws ValidationException {
        validator.validate(user, Objects.nonNull(user.getId()) ? Update.class : Create.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Ищет пользователя по имени
     *
     * @param username имя пользоваиеля
     * @return пользователь
     */
    @Override
    public Optional<User> findByUsername(@NonNull String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Получить всех пользователей
     *
     * @return список пользователей
     */
    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Удаляет пользователя по идентификатору
     *
     * @param user пользователь
     */
    @Override
    public void delete(@NonNull User user) {
        validator.validate(user, Delete.class);
        userRepository.deleteById(user.getId());
    }
}
