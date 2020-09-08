package ru.utelksp.upo.service;

import org.springframework.lang.NonNull;
import ru.utelksp.upo.domain.dictionary.TypeUsing;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Created by ZotovES on 18.03.2019
 * Cервис для видов использования {@link TypeUsing}
 */
public interface TypeUsingService {
    /**
     * Возвращает список всех видов использования
     *
     * @return вид использования.
     */
    @NonNull
    Collection<TypeUsing> findAll();

    /**
     * Возвращает вид использования по идентификатору.
     *
     * @param id идентификатор вида использования.
     * @return вид использования.
     */
    Optional<TypeUsing> findById(@NonNull Long id);

    /**
     * Сохраняет вид использования
     *
     * @param typeUsing вид использования
     * @return сохраненый вид использования
     */
    @NonNull
    TypeUsing save(@NonNull TypeUsing typeUsing);

    /**
     * Удаляет вид использования по идентификатору
     *
     * @param typeUsing вида использования
     */
    void delete(@NonNull TypeUsing typeUsing);

}
