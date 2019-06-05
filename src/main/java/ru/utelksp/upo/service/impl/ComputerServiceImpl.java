package ru.utelksp.upo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.utelksp.upo.common.validators.ComputerValidator;
import ru.utelksp.upo.common.validators.validator.hints.Delete;
import ru.utelksp.upo.domain.dictionary.Computer;
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
    private final ComputerValidator validator;

    /**
     * Возвращает список всех компьютеров
     *
     * @return список компьютеров.
     */
    @NonNull
    @Override
    @Transactional(readOnly = true)
    public Collection<Computer> findAll() {
        return computerRepository.findAll(Sort.by("name"));
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

    /**
     * Сохраняет компьютер
     *
     * @param computer компьютер
     * @return сохраненый компьютер
     */
    @NonNull
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Computer save(@NonNull Computer computer) {
        return computerRepository.save(computer);
    }

    /**
     * Удаляет компьютер
     *
     * @param computer компьютер
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(@NonNull Computer computer) {
        validator.validate(computer, Delete.class);
        computerRepository.deleteById(computer.getId());
    }
}
