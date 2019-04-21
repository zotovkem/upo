package ru.utelksp.upo.view.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.vaadin.crudui.crud.CrudListener;
import ru.utelksp.upo.domain.Program;
import ru.utelksp.upo.service.ProgramService;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Created by ZotovES on 31.03.2019
 * Литснер для редактирования Программ
 */
@Component
@RequiredArgsConstructor
public class ProgramCrudListener implements CrudListener<Program> {
    private final ProgramService programService;

    /**
     * Получить список всех программ
     *
     * @return список программ
     */
    @Override
    public Collection<Program> findAll() {
        return programService.findAll();
    }

    @Override
    public Program add(Program domainObjectToAdd) {
        return programService.save(domainObjectToAdd);
    }

    @Override
    public Program update(Program domainObjectToUpdate) {
        return programService.save(domainObjectToUpdate);
    }

    @Override
    public void delete(Program domainObjectToDelete) {
        programService.deleteById(domainObjectToDelete.getId());
    }

    /**
     * Получить программу по идентификатору
     *
     * @param id идентификатор
     * @return программа
     */
    public Optional<Program> findById(@NonNull Long id) {
        return programService.findById(id);
    }
}
