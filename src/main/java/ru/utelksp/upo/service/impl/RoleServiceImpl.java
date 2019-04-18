package ru.utelksp.upo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.utelksp.upo.domain.security.Role;
import ru.utelksp.upo.repository.RoleRepository;
import ru.utelksp.upo.service.RoleService;

import java.util.Collection;

/**
 * @author Created by ZotovES on 18.04.2019
 * Реализация сервиса управление ролями пользователя
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    /**
     * Получить список ролей
     *
     * @return список ролей
     */
    @NonNull
    @Override
    public Collection<Role> findAll() {
        return roleRepository.findAll();
    }
}
