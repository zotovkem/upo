package ru.utelksp.upo.service;

import org.springframework.lang.NonNull;
import ru.utelksp.upo.domain.dictionary.Organization;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Created by ZotovES on 18.03.2019
 * Cервис для справочника организаций {@link Organization}
 */
public interface OrganizationService {
    /**
     * Возвращает список всех организаций
     *
     * @return список организаций.
     */
    @NonNull
    Collection<Organization> findAll();

    /**
     * Возвращает организацию по идентификатору.
     *
     * @param id идентификатор компьютера.
     * @return организация.
     */
    Optional<Organization> findById(@NonNull Long id);

    /**
     * Сохраняет организацию
     *
     * @param organization организация
     * @return сохраненая организация
     */
    @NonNull
    Organization save(@NonNull Organization organization);

    /**
     * Удаляет организацию по идентификатору
     *
     * @param id идентификатор организации
     */
    void deleteById(@NonNull Long id);
}
