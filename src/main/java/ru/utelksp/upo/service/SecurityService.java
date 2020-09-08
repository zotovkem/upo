package ru.utelksp.upo.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * @author Created by ZotovES on 31.03.2019
 * Сервис для получения текущего пользователя
 */

public interface SecurityService {

    /**
     * Получмть имя текущего пользователя
     *
     * @return имя пользователя
     */
    Optional<String> currentUsername();

    /**
     * Получить текущего пользователя из контекста приложения
     *
     * @return текущий пользователь
     */
    UserDetails currentUser();

}
