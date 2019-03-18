package ru.utelksp.upo.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.utelksp.upo.domain.Computer;
import ru.utelksp.upo.repository.ComputerRepository;
import ru.utelksp.upo.service.ComputerService;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Created by ZotovES on 18.03.2019
 * Реализация сервиса для справочника компьютеров {@link Computer}
 */
@Service
@RequiredArgsConstructor
public class ComputerServiceImpl implements ComputerService {
    private final ComputerRepository computerRepository;

    /**
     * Возвращает список всех компьютеров
     *
     * @return список компьютеров.
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<Computer> findAll() {
        return computerRepository.findAll();
    }

    /**
     * Возвращает компьютер по идентификатору.
     *
     * @param id идентификатор компьютера.
     * @return компьютер.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Computer> findById(@NonNull Long id) {
        return computerRepository.findById(id);
    }
}
