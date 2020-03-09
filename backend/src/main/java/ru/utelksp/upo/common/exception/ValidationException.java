package ru.utelksp.upo.common.exception;

import java.util.Collection;

/**
 * Created by ZotovES on 12.04.2019
 * Ошибка валидации данных
 */
public class ValidationException extends RuntimeException {

    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    protected ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Создает исключение на основе списка ошибок валидации
     */
    public ValidationException(Collection<String> errors) {
        this(String.join("; ", errors));
    }
}
