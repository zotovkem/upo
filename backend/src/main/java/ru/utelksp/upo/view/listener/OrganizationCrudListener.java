package ru.utelksp.upo.view.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.vaadin.crudui.crud.CrudListener;
import ru.utelksp.upo.domain.dictionary.Organization;
import ru.utelksp.upo.service.OrganizationService;

import java.util.Collection;

/**
 * @author Created by ZotovES on 28.03.2019
 * Листнер для формы справочника Организаций
 */
@Component
@RequiredArgsConstructor
public class OrganizationCrudListener implements CrudListener<Organization> {
    private final OrganizationService organizationService;

    @Override
    public Collection<Organization> findAll() {
        return organizationService.findAll();
    }

    @Override
    public Organization add(Organization domainObjectToAdd) {
        return organizationService.save(domainObjectToAdd);
    }

    @Override
    public Organization update(Organization domainObjectToUpdate) {
        return organizationService.save(domainObjectToUpdate);
    }

    @Override
    public void delete(Organization domainObjectToDelete) {
        organizationService.delete(domainObjectToDelete);
    }
}
