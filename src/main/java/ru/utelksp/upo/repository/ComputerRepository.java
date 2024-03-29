package ru.utelksp.upo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.utelksp.upo.domain.dictionary.Computer;

/**
 * @author Created by ZotovES on 18.03.2019
 * Репозиторий для справочника компьютеров {@link Computer}
 */
@Repository
public interface ComputerRepository extends JpaRepository<Computer, Long> {
}
