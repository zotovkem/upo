package ru.utelksp.upo.view.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.vaadin.crudui.crud.CrudListener;
import ru.utelksp.upo.domain.dictionary.Employee;
import ru.utelksp.upo.service.EmployeeService;

import java.util.Collection;

/**
 * @author Created by ZotovES on 30.03.2019
 * Листнер для формы справочника Пользователей
 */
@Component
@RequiredArgsConstructor
public class EmployeeCrudListener implements CrudListener<Employee> {
    private final EmployeeService employeeService;

    @Override
    public Collection<Employee> findAll() {
        return employeeService.findAll();
    }

    @Override
    public Employee add(Employee domainObjectToAdd) {
        return employeeService.save(domainObjectToAdd);
    }

    @Override
    public Employee update(Employee domainObjectToUpdate) {
        return employeeService.save(domainObjectToUpdate);
    }

    @Override
    public void delete(Employee domainObjectToDelete) {
        employeeService.delete(domainObjectToDelete);
    }
}
