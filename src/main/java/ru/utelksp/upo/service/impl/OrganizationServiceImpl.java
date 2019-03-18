package ru.utelksp.upo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.utelksp.upo.domain.Organization;
import ru.utelksp.upo.repository.OrganizationRepository;
import ru.utelksp.upo.service.OrganizationService;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Created by ZotovES on 18.03.2019
 * Реализация сервис для справочника организаций
 */
@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;

    /**
     * Возвращает список всех организаций
     *
     * @return список организаций.
     */
    @NonNull
    @Override
    public Collection<Organization> findAll() {
        return organizationRepository.findAll();
    }

    /**
     * Возвращает организацию по идентификатору.
     *
     * @param id идентификатор компьютера.
     * @return организация.
     */
    @Override
    public Optional<Organization> findById(@NonNull Long id) {
        return organizationRepository.findById(id);
    }
}
