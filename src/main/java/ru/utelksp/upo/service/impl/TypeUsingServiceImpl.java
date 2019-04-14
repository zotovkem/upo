package ru.utelksp.upo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.utelksp.upo.common.validators.TypeUsingValidator;
import ru.utelksp.upo.common.validators.validator.hints.Delete;
import ru.utelksp.upo.domain.dictionary.TypeUsing;
import ru.utelksp.upo.repository.TypeUsingRepository;
import ru.utelksp.upo.service.TypeUsingService;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Created by ZotovES on 18.03.2019
 * Реализация сервиса для видов использования
 */
@Service
@RequiredArgsConstructor
public class TypeUsingServiceImpl implements TypeUsingService {
    private final TypeUsingRepository typeUsingRepository;
    private final TypeUsingValidator validator;

    /**
     * Возвращает список всех видов использования
     *
     * @return вид использования.
     */
    @NonNull
    @Override
    public Collection<TypeUsing> findAll() {
        return typeUsingRepository.findAll();
    }

    /**
     * Возвращает вид использования по идентификатору.
     *
     * @param id идентификатор вида использования.
     * @return вид использования.
     */
    @Override
    public Optional<TypeUsing> findById(@NonNull Long id) {
        return typeUsingRepository.findById(id);
    }

    /**
     * Сохраняет вид использования
     *
     * @param typeUsing вид использования
     * @return сохраненый вид использования
     */
    @NonNull
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TypeUsing save(@NonNull TypeUsing typeUsing) {
        return typeUsingRepository.save(typeUsing);
    }

    /**
     * Удаляет вид использования по идентификатору
     *
     * @param typeUsing идентификатор вида использования
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(@NonNull TypeUsing typeUsing) {
        validator.validate(typeUsing, Delete.class);
        typeUsingRepository.deleteById(typeUsing.getId());
    }
}
