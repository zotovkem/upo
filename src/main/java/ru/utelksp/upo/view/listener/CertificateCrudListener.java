package ru.utelksp.upo.view.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.vaadin.crudui.crud.CrudListener;
import ru.utelksp.upo.domain.Certificate;
import ru.utelksp.upo.service.CertificateService;

import java.util.Collection;

/**
 * @author Created by ZotovES on 30.03.2019
 * Листнер для формы Сертификатов
 */
@Component
@RequiredArgsConstructor
public class CertificateCrudListener implements CrudListener<Certificate> {
    private final CertificateService certificateService;

    @Override
    public Collection<Certificate> findAll() {
        return certificateService.findByAllEager();
    }

    @Override
    public Certificate add(Certificate domainObjectToAdd) {
        return certificateService.save(domainObjectToAdd);
    }

    @Override
    public Certificate update(Certificate domainObjectToUpdate) {
        return certificateService.save(domainObjectToUpdate);
    }

    @Override
    public void delete(Certificate domainObjectToDelete) {
        certificateService.deleteById(domainObjectToDelete.getId());
    }
}
