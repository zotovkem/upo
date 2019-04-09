package ru.utelksp.upo.common.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.utelksp.upo.domain.security.User;
import ru.utelksp.upo.service.UserService;

/**
 * @author Created by ZotovES on 08.04.2019
 * Валидатор учетных данных
 */

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UserService userService;

    @Override
    public boolean supports(@NonNull Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(@NonNull Object o, @NonNull Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Required");
        if (user.getUsername().length() < 8 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }

        if (userService.findByUsername(user.getUsername()).isPresent()) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "Different.userForm.password");
        }
    }
}
