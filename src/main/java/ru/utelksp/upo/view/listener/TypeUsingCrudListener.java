package ru.utelksp.upo.view.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.vaadin.crudui.crud.CrudListener;
import ru.utelksp.upo.domain.dictionary.TypeUsing;
import ru.utelksp.upo.service.TypeUsingService;

import java.util.Collection;

/**
 * @author Created by ZotovES on 31.03.2019
 * Листнере управления видами использования ПО
 */
@Component
@RequiredArgsConstructor
public class TypeUsingCrudListener implements CrudListener<TypeUsing> {
    private final TypeUsingService typeUsingService;

    @Override
    public Collection<TypeUsing> findAll() {
        return typeUsingService.findAll();
    }

    @Override
    public TypeUsing add(TypeUsing domainObjectToAdd) {
        return typeUsingService.save(domainObjectToAdd);
    }

    @Override
    public TypeUsing update(TypeUsing domainObjectToUpdate) {
        return typeUsingService.save(domainObjectToUpdate);
    }

    @Override
    public void delete(TypeUsing domainObjectToDelete) {
        typeUsingService.delete(domainObjectToDelete);
    }
}
