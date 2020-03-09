package ru.utelksp.upo.common.validators.validator;


import ru.utelksp.upo.common.exception.ValidationException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ZotovES on 12.04.2019
 * Интерфейс валидатора
 */
public interface Validator<T> {

    /**
     * Метод валидирующий сущность
     *
     * @param target сущность для валидации
     * @param hint   Объект подсказка если требуется валидация по условию
     * @param errors список ошибок
     */
    void validate(T target, Object hint, Collection<String> errors);

    /**
     * Метод валидирует сущность и кидает исключение если были ошибки валидации
     *
     * @param target целевая сущность
     * @param hint   Объект подсказка если требуется валидация по условию
     * @throws ValidationException исключение содержащее сообщения оь ошибках
     */
    default void validate(T target, Object hint) throws ValidationException {
        ArrayList<String> errors = new ArrayList<>();

        validate(target, hint, errors);

        if (!errors.isEmpty())
            throw new ValidationException(errors);
    }

    /**
     * Метод валидации коллекции сущностей
     *
     * @param targetList список сущностей для валидации
     * @param hint       Объект подсказка если требуется валидация по условию
     * @throws ValidationException исключение содержащее сообщения оь ошибках
     */
    default void validate(Collection<T> targetList, Object hint) throws ValidationException {
        ArrayList<String> errors = new ArrayList<>();

        targetList.forEach(comment -> validate(comment, hint, errors));

        if (!errors.isEmpty())
            throw new ValidationException(errors);
    }
}