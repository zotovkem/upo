package ru.utelksp.upo.service;

import org.springframework.lang.NonNull;
import ru.utelksp.upo.domain.security.Role;

import java.util.Collection;

/**
 * @author Created by ZotovES on 18.04.2019
 * Сервис для управления ролями пользователя
 */
public interface RoleService {

    /**
     * Получить список ролей
     *
     * @return список ролей
     */
    @NonNull
    Collection<Role> findAll();
}
