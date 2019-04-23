package ru.utelksp.upo.domain.dictionary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.utelksp.upo.service.impl.CrudOperationListener;

import javax.persistence.*;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static ru.utelksp.upo.common.Util.getFirstSymbolWithDot;

/**
 * Created by ZotovES on 21.03.2019
 * Справочник пользователей
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(CrudOperationListener.class)
@Table(name = "s_employee", schema = "upo")
public class Employee {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Фамилия
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Отчество
     */
    @Column(name = "patronymic")
    private String patronymic;

    /**
     * Отдел
     */
    @Column(name = "department")
    private String department;

    /**
     * Описание
     */
    @Column(name = "description")
    private String description;

    public String getShortFio() {
        return format("%s %s%s", isNull(lastName) ? "" : lastName,
                getFirstSymbolWithDot(isNull(firstName) ? "" : firstName),
                getFirstSymbolWithDot(isNull(patronymic) ? "" : patronymic));
    }
}
