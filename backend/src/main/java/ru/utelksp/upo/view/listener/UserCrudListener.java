package ru.utelksp.upo.view.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.vaadin.crudui.crud.CrudListener;
import ru.utelksp.upo.domain.security.User;
import ru.utelksp.upo.service.UserService;

import java.util.Collection;

/**
 * @author Created by ZotovES on 07.04.2019
 * Листенер управления пользователями
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserCrudListener implements CrudListener<User> {
    private final UserService userService;

    /**
     * Получить всех пользователей.
     *
     * @return список пользователей.
     */
    @Override
    public Collection<User> findAll() {
        return userService.findAll();
    }

    /**
     * Редактировать пользователя
     *
     * @param domainObjectToUpdate пользователь
     * @return сохраненый пользователь
     */
    @Override
    public User update(User domainObjectToUpdate) {
        return userService.save(domainObjectToUpdate);
    }

    /**
     * Удалить пользователя
     *
     * @param domainObjectToDelete пользователь
     */
    @Override
    public void delete(User domainObjectToDelete) {
        userService.delete(domainObjectToDelete);
    }

    /**
     * Добавить пользователя
     *
     * @param domainObjectToAdd пользователь
     * @return сохраненный пользователь
     */
    @Override
    public User add(User domainObjectToAdd) {
        return userService.save(domainObjectToAdd);
    }
}
