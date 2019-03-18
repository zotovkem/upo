package ru.utelksp.upo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.utelksp.upo.domain.TypeUsing;

/**
 * @author Created by ZotovES on 18.03.2019
 * Репозиторий для справочника видов использования {@link TypeUsing}
 */
@Repository
public interface TypeUsingRepository extends JpaRepository<TypeUsing, Long> {
}
