package ru.utelksp.upo.service;

import org.springframework.lang.NonNull;
import ru.utelksp.upo.domain.dictionary.Computer;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Created by ZotovES on 18.03.2019
 * Cервис для справочника компьютеров {@link Computer}
 */
public interface ComputerService {
    /**
     * Возвращает список всех компьютеров
     *
     * @return список компьютеров.
     */
    @NonNull
    Collection<Computer> findAll();

    /**
     * Возвращает компьютер по идентификатору.
     *
     * @param id идентификатор компьютера.
     * @return компьютер.
     */
    Optional<Computer> findById(@NonNull Long id);

    /**
     * Сохраняет компьютер
     *
     * @param computer компьютер
     * @return сохраненый компьютер
     */
    @NonNull
    Computer save(@NonNull Computer computer);

    /**
     * Удаляет компьютер
     *
     * @param computer компьютер
     */
    void delete(@NonNull Computer computer);
}
