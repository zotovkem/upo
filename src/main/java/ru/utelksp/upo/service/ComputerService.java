package ru.utelksp.upo.service;

import lombok.NonNull;
import ru.utelksp.upo.domain.Computer;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Created by ZotovES on 18.03.2019
 * Реализация сервиса для справочника компьютеров {@link Computer}
 */
public interface ComputerService {
    /**
     * Возвращает список всех компьютеров
     *
     * @return список компьютеров.
     */
    Collection<Computer> findAll();

    /**
     * Возвращает компьютер по идентификатору.
     *
     * @param id идентификатор компьютера.
     * @return компьютер.
     */
    Optional<Computer> findById(@NonNull Long id);
}
