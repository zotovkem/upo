package ru.utelksp.upo.service.security.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.utelksp.upo.domain.security.User;
import ru.utelksp.upo.repository.RoleRepository;
import ru.utelksp.upo.repository.UserRepository;
import ru.utelksp.upo.service.UserService;

import java.util.Optional;
import java.util.Set;

/**
 * @author Created by ZotovES on 18.03.2019
 * Реализация сервиса управления пользователями
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(roleRepository.getOne(2L)));
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
}