package ru.utelksp.upo.service;

import org.springframework.lang.NonNull;
import ru.utelksp.upo.domain.Program;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Created by ZotovES on 23.03.2019
 * Сервис для управления Программами
 */
public interface ProgramService {
    /**
     * Получить все программы
     *
     * @return список программ
     */
    @NonNull
    Collection<Program> findAll();

    /**
     * Получить программу по идентификатору
     *
     * @param id идентификатор программы
     * @return программа
     */
    Optional<Program> findById(@NonNull Long id);

    /**
     * Сохранить программу
     *
     * @param program программа
     * @return сохранненая программма
     */
    @NonNull
    Program save(@NonNull Program program);

    /**
     * Удалить программу по идентификатору
     *
     * @param id идентификатор программы
     */
    void deleteById(@NonNull Long id);
}
