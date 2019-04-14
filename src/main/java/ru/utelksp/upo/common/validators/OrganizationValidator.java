package ru.utelksp.upo.common.validators;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.utelksp.upo.common.validators.validator.Validator;
import ru.utelksp.upo.common.validators.validator.hints.Delete;
import ru.utelksp.upo.domain.Certificate;
import ru.utelksp.upo.domain.Order;
import ru.utelksp.upo.domain.dictionary.Employee;
import ru.utelksp.upo.domain.dictionary.Organization;
import ru.utelksp.upo.repository.OrderRepository;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author Created by ZotovES on 14.04.2019
 * Валидатор организаций
 */

@Component
@RequiredArgsConstructor
public class OrganizationValidator implements Validator<Organization> {

    private final OrderRepository orderRepository;

    /**
     * Метод валидирующий сущность
     *
     * @param target сущность для валидации
     * @param hint   Объект подсказка если требуется валидация по условию
     * @param errors список ошибок
     */
    @Override
    public void validate(Organization target, Object hint, Collection<String> errors) {
        if (hint.equals(Delete.class)) {
            orderRepository.findByOrganizationId(target.getId()).forEach(getTextErrorOrder(target, errors));
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
    private Consumer<Order> getTextErrorOrder(Organization target, Collection<String> errors) {
        return order -> errors.add(String.format("Организация %s %s используется в приказе номер %s от %s",
                target.getId(), target.getName(), order.getOrderNumber(), order.getOrderDate()));
    }

    /**
     * Формирует текст ошибки при наличии связанных сущностей
     *
     * @param target проверяемая сущность
     * @param errors список ошибок
     */
    @NotNull
    private Consumer<Certificate> getTextErrorCertificate(Employee target, Collection<String> errors) {
        return certificate -> errors.add(String.format("Пользователь %s %s используется в сертификате %s %s",
                target.getId(), target.getShortFio(), certificate.getId(), certificate.getName()));
    }
}
