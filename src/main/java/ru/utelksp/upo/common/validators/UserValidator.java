package ru.utelksp.upo.common.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.utelksp.upo.common.validators.validator.Validator;
import ru.utelksp.upo.common.validators.validator.hints.Delete;
import ru.utelksp.upo.domain.security.User;
import ru.utelksp.upo.repository.UserRepository;

import java.util.Collection;

import static java.util.Objects.isNull;

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
        if (hint.equals(Delete.class) && userRepository.count() <= 1) {
            errors.add("Нельзя удалить единственного пользователя");
            return;
        }

        if (hint.equals(Delete.class)) {
            return;
        }

        if (isNull(user.getPassword())) {
            errors.add("Не задан пароль");
            return;
        }

        if (isNull(user.getConfirmPassword())) {
            errors.add("Повторите пароль");
            return;
        }

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
