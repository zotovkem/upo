package ru.utelksp.upo.view.component;

import com.vaadin.flow.component.ItemLabelGenerator;
import org.jetbrains.annotations.NotNull;
import ru.utelksp.upo.domain.Certificate;
import ru.utelksp.upo.domain.Order;
import ru.utelksp.upo.domain.dictionary.Computer;
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
     * Получить подписи из ордера для итемов в комбобокса ордеров
     */
    @NotNull
    public static ItemLabelGenerator<Order> getItemListEmployees() {
        return order -> order.getEmployees().stream()
                .map(Employee::getShortFio)
                .collect(Collectors.joining(", "));
    }

    /**
     * Получить подписи из сертификата для итемов в комбобокса пользователей
     */
    @NotNull
    public static ItemLabelGenerator<Certificate> getItemEmployeeOfCertificate() {
        return certificate -> Optional.ofNullable(certificate)
                .map(Certificate::getEmployee)
                .map(Employee::getShortFio).orElse("");
    }

    /**
     * Получить подписи из сертификата для итемов в комбобокса пользователей
     */
    @NotNull
    public static ItemLabelGenerator<Employee> getItemEmployee() {
        return employee -> Optional.ofNullable(employee)
                .map(Employee::getShortFio).orElse("");
    }

    /**
     * Получить подписи из компьютера для итемов в комбобокса компьютера
     */
    @NotNull
    public static ItemLabelGenerator<Computer> getItemComputer() {
        return com -> Optional.ofNullable(com)
                .map(Computer::getName)
                .orElse("");
    }

    /**
     * Получить подписи из организации для итемов в комбобокса организаций
     */
    @NotNull
    public static ItemLabelGenerator<Organization> getItemOrganization() {
        return o -> Optional.ofNullable(o)
                .map(Organization::getName)
                .orElse("");
    }

    /**
     * Получить подписи из сертификата для итемов в комбобокса сертификатов
     */
    @NotNull
    public static ItemLabelGenerator<Certificate> getItemCertificate() {
        return o -> Optional.ofNullable(o)
                .map(Certificate::getName)
                .orElse("");
    }

    /**
     * Получить подписи из Видов использования для итемов в комбобокса вид использования
     */
    @NotNull
    public static ItemLabelGenerator<TypeUsing> getItemTypeUsing() {
        return o -> Optional.ofNullable(o)
                .map(TypeUsing::getName)
                .orElse("");
    }


}
