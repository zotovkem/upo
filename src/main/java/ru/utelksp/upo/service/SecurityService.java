package ru.utelksp.upo.service;

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
    Optional<String> currentUser();

}
