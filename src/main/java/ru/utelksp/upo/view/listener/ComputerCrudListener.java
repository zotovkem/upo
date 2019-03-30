package ru.utelksp.upo.view.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.vaadin.crudui.crud.CrudListener;
import ru.utelksp.upo.domain.dictionary.Computer;
import ru.utelksp.upo.service.ComputerService;

import java.util.Collection;

/**
 * @author Created by ZotovES on 28.03.2019
 * Листнер для формы справочника Компьютеры
 */
@Component
@RequiredArgsConstructor
public class ComputerCrudListener implements CrudListener<Computer>{
    private final ComputerService computerService;

    @Override
    public Collection<Computer> findAll() {
        return computerService.findAll();
    }

    @Override
    public Computer add(Computer domainObjectToAdd) {
        return computerService.save(domainObjectToAdd);
    }

    @Override
    public Computer update(Computer domainObjectToUpdate) {
        return computerService.save(domainObjectToUpdate);
    }

    @Override
    public void delete(Computer domainObjectToDelete) {
        computerService.deleteById(domainObjectToDelete.getId());
    }
}
