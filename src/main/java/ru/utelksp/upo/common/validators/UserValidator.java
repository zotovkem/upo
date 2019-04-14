package ru.utelksp.upo.common.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.utelksp.upo.SecurityConfig;
import ru.utelksp.upo.common.validators.validator.Validator;
import ru.utelksp.upo.domain.security.User;
import ru.utelksp.upo.repository.UserRepository;
import ru.utelksp.upo.service.UserService;

import java.util.Collection;

/**
 * @author Created by ZotovES on 08.04.2019
 * Валидатор учетных данных
 */

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator<User> {

    private final UserRepository userRepository;

    /**
     * Метод валидирующий сущность
     *
     * @param user   сущность для валидации
     * @param hint   Объект подсказка если требуется валидация по условию
     * @param errors список ошибок
     */
    @Override
    public void validate(User user, Object hint, Collection<String> errors) {
        if (user.getConfirmPassword().length() < 8) {
            errors.add("Длина пароля менее 8 символов");
        }

        if (user.getConfirmPassword().length() > 32) {
            errors.add("Длина пароля более 32 символов");
        }

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            errors.add("Уже существует пользователь с таким именем");
        }

        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.add("Пароли не совпадают");
        }
    }

}
