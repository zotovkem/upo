package ru.utelksp.upo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.utelksp.upo.domain.TypeUsing;
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
}
