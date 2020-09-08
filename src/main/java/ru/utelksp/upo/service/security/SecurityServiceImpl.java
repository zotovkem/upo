package ru.utelksp.upo.service.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.utelksp.upo.service.SecurityService;

import java.util.Optional;

/**
 * @author Created by ZotovES on 31.03.2019
 * Реализация сервиса получения текущего пользователя
 */

@Service
public class SecurityServiceImpl implements SecurityService {

    /**
     * Получить имя пользователя из контекста
     *
     * @return имя текущего пользователя
     */
    @Override
    public Optional<String> currentUsername() {
        return Optional.of(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .filter(o -> o instanceof UserDetails)
                .map(o -> (UserDetails) o)
                .map(UserDetails::getUsername);
    }

    /**
     * Получить текущего пользователя из контекста приложения
     *
     * @return текущий пользователь
     */
    @Override
    public UserDetails currentUser() {
        return Optional.of(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .filter(o -> o instanceof UserDetails)
                .map(o -> (UserDetails) o)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден!"));
    }
}
