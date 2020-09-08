package ru.utelksp.upo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.utelksp.upo.domain.dictionary.Organization;

/**
 * @author Created by ZotovES on 18.03.2019
 * Репозиторий для справочника организаций {@link Organization}
 */
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
