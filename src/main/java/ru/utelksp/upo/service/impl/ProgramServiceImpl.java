package ru.utelksp.upo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.utelksp.upo.domain.Program;
import ru.utelksp.upo.repository.ProgramRepository;
import ru.utelksp.upo.service.ProgramService;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Created by ZotovES on 23.03.2019
 * Реализация сервиса управления программами
 */
@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {
    private final ProgramRepository programRepository;

    /**
     * Получить все программы
     *
     * @return список программ
     */
    @NonNull
    @Override
    public Collection<Program> findAll() {
        return programRepository.findAll();
    }

    /**
     * Получить программу по идентификатору
     *
     * @param id идентификатор программы
     * @return программа
     */
    @Override
    public Optional<Program> findById(@NonNull Long id) {
        return programRepository.findById(id);
    }

    /**
     * Сохранить программу
     *
     * @param program программа
     * @return сохранненая программма
     */
    @NonNull
    @Override
    public Program save(@NonNull Program program) {
        return programRepository.save(program);
    }

    /**
     * Удалить программу по идентификатору
     *
     * @param id идентификатор программы
     */
    @Override
    public void deleteById(@NonNull Long id) {
        programRepository.deleteById(id);
    }
}