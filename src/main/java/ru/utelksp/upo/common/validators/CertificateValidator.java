package ru.utelksp.upo.common.validators;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.utelksp.upo.common.validators.validator.Validator;
import ru.utelksp.upo.common.validators.validator.hints.Create;
import ru.utelksp.upo.common.validators.validator.hints.Delete;
import ru.utelksp.upo.common.validators.validator.hints.Update;
import ru.utelksp.upo.domain.Certificate;
import ru.utelksp.upo.domain.Order;
import ru.utelksp.upo.repository.OrderRepository;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author Created by ZotovES on 22.04.2019
 * Валидатор сертификатов
 */

@Component
@RequiredArgsConstructor
public class CertificateValidator implements Validator<Certificate> {

    private final OrderRepository orderRepository;

    /**
     * Метод валидирующий сущность
     *
     * @param target сущность для валидации
     * @param hint   Объект подсказка если требуется валидация по условию
     * @param errors список ошибок
     */
    @Override
    public void validate(Certificate target, Object hint, Collection<String> errors) {
        if (hint.equals(Delete.class)) {
            orderRepository.findByCertificateId(target.getId()).forEach(getTextErrorOrder(target, errors));
        }

        if (hint.equals(Update.class) || hint.equals(Create.class)) {
            errors.add("Наименование сертификата должно быть заполнено");
        }

        if (!errors.isEmpty()) {
            errors.add("Не возможно удалить запись, отредактируйте зависимые записи");
        }
    }

    /**
     * Формирует текст ошибки при наличии связанных сущностей
     *
     * @param target проверяемая сущность
     * @param errors список ошибок
     */
    @NotNull
    private Consumer<Order> getTextErrorOrder(Certificate target, Collection<String> errors) {
        return order -> errors.add(String.format("Сертификат %s %s используется в приказе номер %s от %s",
                target.getId(), target.getName(), order.getOrderNumber(), order.getOrderDate()));
    }

}
