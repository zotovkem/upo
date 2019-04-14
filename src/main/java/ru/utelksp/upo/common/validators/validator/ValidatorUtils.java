package ru.utelksp.upo.common.validators.validator;

import lombok.NonNull;
import org.springframework.util.StringUtils;
import ru.utelksp.upo.common.validators.validator.hints.Create;
import ru.utelksp.upo.common.validators.validator.hints.Update;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * Created by ZotovES on 12.04.2019
 * Утилиты для валидации
 */
public class ValidatorUtils {

    private ValidatorUtils() {
    }

    /**
     * Валидирование на наличие ID при обновлении, и отсутствии при создании
     *
     * @param supplier метод получения идентификатиора
     * @param hint     Тип операции
     * @param errors   список ошибок
     * @param <T>      тип сущности
     */
    public static <T> void validateByAction(Supplier<T> supplier, Object hint, Collection<String> errors) {
        if (hint.equals(Create.class) && supplier.get() != null)
            errors.add("При создании сущности идентификатор должен быть пустым");
        if (hint.equals(Update.class) && supplier.get() == null)
            errors.add("При изменении сущности идентификатор должен быть явно задан");
    }

    /***
     * Проверка наличия в базе ущности со стондартным сообщением
     *
     * @param existenceSupplier поставшик признака существования
     * @param errors список ошибок
     */
    public static void validateDbExistence(Supplier<Boolean> existenceSupplier, Collection<String> errors) {
        validateDbExistence(existenceSupplier, errors, "Сущности с таким идентификатором не существует в базе данных");
    }

    /**
     * Проверка наличия в базе ущности с кастомным сообщением
     *
     * @param existenceSupplier поставщик признака существования
     * @param errors            список сообщения
     * @param errorMessage      сообщений
     */
    public static void validateDbExistence(Supplier<Boolean> existenceSupplier, Collection<String> errors, String errorMessage) {
        if (!existenceSupplier.get())
            errors.add(errorMessage);
    }

    /**
     * проверка на пустое значчение
     *
     * @param supplier     поставщик значения
     * @param errors       список ошибок
     * @param errorMessage сообщение о ошибке
     * @param <T>          тип значения
     */
    public static <T> void validateNotNull(Supplier<T> supplier, Collection<String> errors, String errorMessage) {
        if (supplier.get() == null)
            errors.add(errorMessage);
    }

    /**
     * Валидация на эквивалентностьт
     *
     * @param source  значение
     * @param target  значение для сравнения
     * @param errors  список с ошибкам
     * @param message сообщение
     * @param <T>     тип значея
     */
    public static <T> void validateEquals(T source, @NonNull Object target, Collection<String> errors, String message) {
        if (!target.equals(source))
            errors.add(message);
    }

    /**
     * проверяем на валидность Электронный адрес
     *
     * @param value - адрес электронной почты
     * @return
     */
    public static boolean isEmailValid(final String value) {

        return !StringUtils.isEmpty(value) && Pattern.compile("^([\\-_.a-zA-Zа-яА-Я0-9]+)@([\\-_a-zA-Zа-яА-Я0-9]+)\\.([a-zA-Zа-яА-Я]{2,6})$").matcher(value).matches();

    }

}
