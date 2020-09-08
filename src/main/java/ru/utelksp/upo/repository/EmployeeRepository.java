package ru.utelksp.upo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.utelksp.upo.domain.dictionary.Employee;

/**
 * @author Created by ZotovES on 21.03.2019
 * Репозиторий для справочника пользователей {@link Employee}
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
