package ru.utelksp.upo.view.component;

import com.vaadin.flow.component.ItemLabelGenerator;
import org.jetbrains.annotations.NotNull;
import ru.utelksp.upo.domain.Certificate;
import ru.utelksp.upo.domain.Order;
import ru.utelksp.upo.domain.dictionary.Employee;
import ru.utelksp.upo.domain.dictionary.Organization;
import ru.utelksp.upo.domain.dictionary.TypeUsing;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Created by ZotovES on 23.04.2019
 * Фабрика вспомогательных компонентов
 */
public class FactoryComponent {
    /**
     * Получить подписи для итемов в комбобокса ордеров
     */
    @NotNull
    public static ItemLabelGenerator<Order> getOrderItemLabelGenerator() {
        return order -> order.getEmployees().stream().map(Employee::getShortFio).collect(Collectors.joining(", "));
    }

    /**
     * Получить подписи для итемов в комбобокса организаций
     */
    @NotNull
    public static ItemLabelGenerator<Organization> getOrganizationItemLabelGenerator() {
        return o -> Optional.ofNullable(o).map(Organization::getName).orElse("");
    }

    /**
     * Получить подписи для итемов в комбобокса сертификатов
     */
    @NotNull
    public static ItemLabelGenerator<Certificate> getCertificateItemLabelGenerator() {
        return o -> Optional.ofNullable(o).map(Certificate::getName).orElse("");
    }

    /**
     * Получить подписи для итемов в комбобокса вид использования
     */
    @NotNull
    public static ItemLabelGenerator<TypeUsing> getTypeUsingItemLabelGenerator() {
        return o -> Optional.ofNullable(o).map(TypeUsing::getName).orElse("");
    }


}
