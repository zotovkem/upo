package ru.utelksp.upo.service;

import org.springframework.lang.NonNull;
import ru.utelksp.upo.domain.dictionary.Employee;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Created by ZotovES on 21.03.2019
 * Сервис для работы со справочником Пользователей.
 */
public interface EmployeeService {
    /**
     * Получить всех пользователей
     *
     * @return список пользователей
     */
    Collection<Employee> findAll();

    /**
     * Получить пользователя по идентификатору
     *
     * @param id идентификатор пользователя
     * @return пользователь
     */
    @NonNull
    Optional<Employee> findById(@NonNull Long id);

    /**
     * Сохраняет пользователя
     *
     * @param employee пользователь
     * @return сохраненый пользователь
     */
    @NonNull
    Employee save(@NonNull Employee employee);

    /**
     * Удаляет пользователя
     *
     * @param employee идентификатор пользователя
     */
    void delete(@NonNull Employee employee);
}
