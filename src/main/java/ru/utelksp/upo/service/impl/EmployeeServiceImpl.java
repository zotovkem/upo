package ru.utelksp.upo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.utelksp.upo.domain.Employee;
import ru.utelksp.upo.repository.EmployeeRepository;
import ru.utelksp.upo.service.EmployeeService;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Created by ZotovES on 21.03.2019
 * Реализация сервиса для справочника пользователей
 */
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    /**
     * Получить всех пользователей
     *
     * @return список пользователей
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<Employee> findAll() {
        return employeeRepository.findAll();
    }

    /**
     * Получить пользователя по идентификатору
     *
     * @param id идентификатор пользователя
     * @return пользователь
     */
    @NonNull
    @Override
    @Transactional(readOnly = true)
    public Optional<Employee> findById(@NonNull Long id) {
        return employeeRepository.findById(id);
    }

    /**
     * Сохраняет пользователя
     *
     * @param employee пользователь
     * @return сохраненый пользователь
     */
    @NonNull
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Employee save(@NonNull Employee employee) {
        return employeeRepository.save(employee);
    }

    /**
     * Удаляет пользователя по идентификатору
     *
     * @param id идентификатор пользователя
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(@NonNull Long id) {
        employeeRepository.deleteById(id);
    }
}